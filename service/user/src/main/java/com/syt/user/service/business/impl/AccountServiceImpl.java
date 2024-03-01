package com.syt.user.service.business.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.syt.api.mail.MailSendClient;
import com.syt.model.common.dtos.res.Response;
import com.syt.model.common.enums.HttpCode;
import com.syt.model.mail.dtos.req.SendButtonMailRequest;
import com.syt.model.user.dos.UserActivate;
import com.syt.model.user.dos.UserAuth;
import com.syt.model.user.dos.UserInfo;
import com.syt.model.user.dos.UserState;
import com.syt.model.user.dtos.req.LoginRequest;
import com.syt.model.user.dtos.req.RegisterRequest;
import com.syt.model.user.dtos.res.ActivateResponse;
import com.syt.model.user.dtos.res.LoginResponse;
import com.syt.model.user.dtos.res.RegisterResponse;
import com.syt.user.mapper.business.AccountMapper;
import com.syt.user.service.business.AccountService;
import com.syt.user.service.data.UserActivateService;
import com.syt.user.service.data.UserAuthService;
import com.syt.user.service.data.UserInfoService;
import com.syt.user.service.data.UserStateService;
import com.syt.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@Transactional
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountMapper accountMapper;
    @Resource
    private UserAuthService userAuthService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserActivateService userActivateService;
    @Resource
    private UserStateService userStateService;

    @Resource
    private MailSendClient mailSendClient;

    @Resource
    private DiscoveryClient discoveryClient;

    private List<ServiceInstance> getServiceInstanceList(String name) {
        return discoveryClient.getInstances(name);
    }

    private String getSocket(String name) {
        List<ServiceInstance> serviceInstanceList = getServiceInstanceList(name);
        ServiceInstance serviceInstance = serviceInstanceList.get(new Random().nextInt(serviceInstanceList.size()));
        return serviceInstance.getUri().toString();
    }

    /**
     * 登录
     *
     * @param request
     * @return
     */
    @Override
    public Response<LoginResponse> login(LoginRequest request) {
        // 1 获取邮箱与密码
        String email = request.getEmail();
        String password = request.getPassword();

        // 2 校验邮箱与密码
        if (StringUtils.isBlank(email) && StringUtils.isBlank(password)) {
            return new Response<>(HttpCode.PARAM_INVALID.getCode(),
                    "邮箱与密码不能为空"
            );
        }

        // 3 根据邮箱查询密码
        UserAuth dbUserAuth = accountMapper.selectActivatedAccountByEmail(email);
        if (dbUserAuth == null) {
            dbUserAuth = accountMapper.selectUnActivatedAccountByEmail(email);
            if (dbUserAuth != null) {
                return new Response<>(HttpCode.DATA_EXIST.getCode(),
                        "邮箱对应账号未激活, 请前往邮箱激活账号"
                );
            }
            return new Response<>(HttpCode.DATA_NOT_EXIST.getCode(),
                    "邮箱对应账号不存在"
            );
        }

        // 4 比对密码
        String salt = dbUserAuth.getSalt();
        String md5Password = DigestUtil.md5Hex(password + salt);
        if (!md5Password.equals(dbUserAuth.getPassword())) {
            return new Response<>(HttpCode.LOGIN_PASSWORD_ERROR.getCode(),
                    "密码错误"
            );
        }

        // 5 返回响应
        Integer id = dbUserAuth.getId();
        UserInfo dbUserInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery()
                .eq(UserInfo::getUserAuthId, id)
        );
        if (dbUserInfo == null) {
            return new Response<>(HttpCode.DATA_NOT_EXIST.getCode(),
                    "用户信息不存在"
            );
        }
        return new Response<>(HttpCode.SUCCESS.getCode(),
                "登录成功",
                new LoginResponse() {{
                    setEmail(email);
                    setUserInfo(dbUserInfo);
                    setToken(JwtUtil.getJwt(id));
                }}
        );
    }

    /**
     * 注册
     *
     * @param request
     * @return
     */
    @Override
    public Response<RegisterResponse> register(RegisterRequest request) {
        // 1 获取邮箱与密码
        String email = request.getEmail();
        String password = request.getPassword();

        // 2 校验邮箱与密码
        if (StringUtils.isBlank(email) && StringUtils.isBlank(password)) {
            return new Response<>(HttpCode.PARAM_INVALID.getCode(),
                    "邮箱与密码不能为空"
            );
        }

        // 3 查询该邮箱是否已注册
        // 3.1 查询已激活账号
        UserAuth dbUserAuth = accountMapper.selectActivatedAccountByEmail(email);
        if (dbUserAuth != null) {
            return new Response<>(HttpCode.DATA_EXIST.getCode(),
                    "邮箱对应账号已存在"
            );
        }
        // 3.2 查询待激活账号
        dbUserAuth = accountMapper.selectUnActivatedAccountByEmail(email);
        // 3.2.1 存在
        if (dbUserAuth != null) {
            // 3.2.1.1 获取激活时间
            UserActivate dbUserActivate = userActivateService.getOne(Wrappers.<UserActivate>lambdaQuery()
                    .select(UserActivate::getId, UserActivate::getDeadline)
                    .eq(UserActivate::getUserAuthId, dbUserAuth.getId())
            );
            // 3.2.1.2 未超过激活时间
            Date currentTime = new Date();
            if (currentTime.getTime() < dbUserActivate.getDeadline().getTime()) {
                return new Response<>(HttpCode.Fail.getCode(),
                        "已发送激活邮件到该邮箱中，请前往邮箱激活账号"
                );
            }
            // 3.2.1.3 激活超时
            // 3.2.1.3.1 更新激活时间
            dbUserActivate.setDeadline(new Date(currentTime.getTime() + 1000 * 60 * 60 * 24));
            // 3.2.1.3.2 更新激活码
            dbUserActivate.setCode(RandomUtil.randomString(9));
            dbUserActivate.setUpdateTime(currentTime);
            userActivateService.updateById(dbUserActivate);
        }
        // 3.2.2 不存在 用户从未注册
        else {
            Date currentTime = new Date();
            // 3.2.2.1 添加用户认证数据
            UserAuth newUserAuth = new UserAuth();
            newUserAuth.setEmail(email);
            // 生成盐
            String salt = RandomUtil.randomString(32);
            newUserAuth.setSalt(salt);
            newUserAuth.setPassword(DigestUtil.md5Hex(password + salt));
            newUserAuth.setCreateTime(currentTime);
            newUserAuth.setUpdateTime(currentTime);
            userAuthService.save(newUserAuth);

            Integer newUserAuthId = userAuthService
                    .getOne(Wrappers.<UserAuth>lambdaQuery()
                            .select(UserAuth::getId)
                            .eq(UserAuth::getEmail, email)
                    ).getId();

            // 3.2.2.2 添加用户激活数据
            UserActivate newUserActivate = new UserActivate();
            newUserActivate.setUserAuthId(newUserAuthId);
            // 设置激活码
            newUserActivate.setCode(RandomUtil.randomString(9));
            // 设置激活时间
            newUserActivate.setDeadline(new Date(currentTime.getTime() + 1000 * 60 * 60 * 24));
            newUserActivate.setCreateTime(currentTime);
            newUserActivate.setUpdateTime(currentTime);
            userActivateService.save(newUserActivate);
            // 3.2.2.3 添加用户状态数据
            UserState newUserState = new UserState();
            newUserState.setUserAuthId(newUserAuthId);
            newUserState.setIsActivated(false);
            newUserState.setCreateTime(currentTime);
            newUserState.setUpdateTime(currentTime);
            userStateService.save(newUserState);
        }

        // 4 生成激活密钥
        UserAuth userAuth = userAuthService.getOne(Wrappers.<UserAuth>lambdaQuery()
                .select(UserAuth::getId, UserAuth::getSalt)
                .eq(UserAuth::getEmail, email)
        );
        UserActivate userActivate = userActivateService.getOne(Wrappers.<UserActivate>lambdaQuery()
                .select(UserActivate::getCode)
                .eq(UserActivate::getUserAuthId, userAuth.getId())
        );
        String token = DigestUtil.md5Hex(userActivate.getCode() + userAuth.getSalt());

        // 5 发送认证邮件
        int sendMailResult = sendActivateAccountMail(email, token);
        switch (sendMailResult) {
            case -1:
            case 1:
                return new Response<>(HttpCode.Fail.getCode(), "激活邮件发送失败，请检查邮箱是否正确，并稍后重试");
            case 0:
                break;
        }
        return new Response<>(HttpCode.SUCCESS.getCode(), "注册成功，请在 24 小时内前往邮箱激活账号");
    }

    /**
     * 激活
     *
     * @param email
     * @param token
     * @return
     */
    @Override
    public Response<ActivateResponse> activate(String email, String token) {
        // 1 校验邮箱与密钥
        if (StringUtils.isBlank(email) && StringUtils.isBlank(token)) {
            return new Response<>(HttpCode.PARAM_INVALID.getCode(),
                    "邮箱与密钥不能为空"
            );
        }
        // 2 邮箱是否存在
        UserAuth dbUserAuth = userAuthService.getOne(Wrappers.<UserAuth>lambdaQuery()
                .select(UserAuth::getId, UserAuth::getSalt)
                .eq(UserAuth::getEmail, email)
        );
        if (dbUserAuth == null) {
            return new Response<>(HttpCode.Fail.getCode(),
                    "该邮箱对应账号不存在"
            );
        }
        UserState dbUserState = userStateService.getOne(Wrappers.<UserState>lambdaQuery()
                .select(UserState::getId)
                .eq(UserState::getUserAuthId, dbUserAuth.getId())
                .eq(UserState::getIsActivated, true)
        );
        if (dbUserState != null) {
            return new Response<>(HttpCode.Fail.getCode(),
                    "该邮箱对应账号已经激活好了哦"
            );
        }
        // 3 获取验证码
        UserActivate dbUserActivate = userActivateService.getOne(Wrappers.<UserActivate>lambdaQuery()
                .select(UserActivate::getId, UserActivate::getCode, UserActivate::getDeadline)
                .eq(UserActivate::getUserAuthId, dbUserAuth.getId())
        );
        if (dbUserActivate == null) {
            return new Response<>(HttpCode.DATA_NOT_EXIST.getCode(),
                    "无效的激活链接，请确认后重试"
            );
        }
        // 3.1 验证码过期
        Date currentTime = new Date();
        if (currentTime.getTime() > dbUserActivate.getDeadline().getTime()) {
            return new Response<>(HttpCode.Fail.getCode(),
                    "激活失败，链接已过期"
            );
        }
        // 4 验证 token
        if (!token.equals(DigestUtil.md5Hex(dbUserActivate.getCode() + dbUserAuth.getSalt()))) {
            return new Response<>(HttpCode.Fail.getCode(),
                    "激活失败，密钥错误"
            );
        }
        // 5 验证成功
        // 5.1 更新用户激活数据
        dbUserActivate.setDeadline(currentTime);
        dbUserActivate.setUpdateTime(currentTime);
        userActivateService.updateById(dbUserActivate);
        // 5.2 更新用户状态数据
        UserState userState = new UserState();
        userState.setIsActivated(true);
        userState.setUpdateTime(currentTime);
        userStateService.update(userState, Wrappers.<UserState>lambdaUpdate()
                .eq(UserState::getUserAuthId, dbUserAuth.getId())
        );
        // 5.3 添加用户信息数据
        UserInfo userInfo = new UserInfo();
        userInfo.setUserAuthId(dbUserAuth.getId());
        userInfo.setName("用户" + dbUserAuth.getId());
        userInfoService.save(userInfo);

        // 6 返回响应
        return new Response<>(HttpCode.SUCCESS.getCode(),
                "激活成功，感谢注册蒸汽赛博平台账号"
        );
    }


    private int sendActivateAccountMail(String to, String token) {
        String socket;
        try {
            socket = getSocket("cyber-steam-music-app-gateway");
        } catch (Exception e) {
            return -1;
        }
        String url = socket + "/user/api/v1/account/activate?email=" + to + "&token=" + token;
        SendButtonMailRequest request = new SendButtonMailRequest();
        request.setTo(to);
        request.setTitle("蒸汽赛博 - 账号激活");
        request.setName("亲爱的用户");
        request.setContent("您最近注册了蒸汽赛博的账号，需点击下面的按钮激活账号");
        request.setUrl(url);
        request.setButton("激活账号");
        int result;
        try {
            result = mailSendClient.sendButtonMail(request);
        } catch (Exception e) {
            result = -1;
        }
        return result;
    }

    private int sendResetUserAuthMail(String to, String name, String token) {
        String socket;
        try {
            socket = getSocket("cyber-steam-music-app-gateway");
        } catch (Exception e) {
            return -1;
        }
        String url = socket + "/user/api/v1/account/reset?email=" + to + "&token=" + token;
        SendButtonMailRequest request = new SendButtonMailRequest();
        request.setTo(to);
        request.setTitle("蒸汽赛博 - 修改确认");
        request.setName(name == null ? "亲爱的用户" : name);
        request.setContent("您最近申请了修改重要的用户信息，需点击下面的按钮确认修改");
        request.setUrl(url);
        request.setButton("确认修改");
        int result;
        try {
            result = mailSendClient.sendButtonMail(request);
        } catch (Exception e) {
            result = -1;
        }
        return result;
    }
}