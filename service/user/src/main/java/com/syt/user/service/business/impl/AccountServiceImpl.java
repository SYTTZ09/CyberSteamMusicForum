package com.syt.user.service.business.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.syt.model.common.dtos.res.Response;
import com.syt.model.common.enums.HttpCode;
import com.syt.model.user.dos.UserActivate;
import com.syt.model.user.dos.UserAuth;
import com.syt.model.user.dos.UserInfo;
import com.syt.model.user.dtos.req.LoginRequest;
import com.syt.model.user.dtos.req.RegisterRequest;
import com.syt.model.user.dtos.res.LoginResponse;
import com.syt.model.user.dtos.res.RegisterResponse;
import com.syt.user.mapper.business.AccountMapper;
import com.syt.user.service.business.AccountService;
import com.syt.user.service.data.UserActivateService;
import com.syt.user.service.data.UserAuthService;
import com.syt.user.service.data.UserInfoService;
import com.syt.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

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
            return new Response<>(HttpCode.DATA_EXIST.getCode(),
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
                .eq(UserInfo::getId, id)
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
            dbUserActivate.setDeadline(new Date(currentTime.getTime() + 1000 * 60 * 5));
            // 3.2.1.3.2 更新激活码
            dbUserActivate.setCode(RandomUtil.randomString(4));
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
            // 3.2.2.1 添加用户激活数据
            UserActivate newUserActivate = new UserActivate();
            newUserActivate.setUserAuthId(userAuthService
                    .getOne(Wrappers.<UserAuth>lambdaQuery()
                            .select(UserAuth::getId)
                            .eq(UserAuth::getEmail, email)
                    ).getId()
            );
            // 设置激活码
            newUserActivate.setCode(RandomUtil.randomString(4));
            // 设置激活时间
            newUserActivate.setDeadline(new Date(currentTime.getTime() + 1000 * 60 * 5));
            newUserActivate.setCreateTime(currentTime);
            newUserActivate.setUpdateTime(currentTime);
        }

        // 4 生成认证邮件
        // 5 发送认证邮件
        return null;
    }
}