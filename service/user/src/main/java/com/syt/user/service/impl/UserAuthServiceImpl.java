package com.syt.user.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.syt.model.common.dtos.res.Response;
import com.syt.model.common.enums.HttpCode;
import com.syt.model.user.dos.UserAuth;
import com.syt.model.user.dos.UserInfo;
import com.syt.model.user.dtos.req.LoginRequest;
import com.syt.model.user.dtos.res.LoginResponse;
import com.syt.user.mapper.UserAuthMapper;
import com.syt.user.service.UserAuthService;
import com.syt.user.service.UserInfoService;
import com.syt.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
@Slf4j
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuth> implements UserAuthService {

    @Resource
    private UserInfoService userInfoService;

    /**
     * 登录
     *
     * @param request
     * @return
     */
    @Override
    public Response<LoginResponse> login(LoginRequest request) {
        // 获取邮箱与密码
        String email = request.getEmail();
        String password = request.getPassword();

        // 校验邮箱与密码
        if (!StringUtils.isNotBlank(email) && !StringUtils.isNotBlank(password)) {
            return new Response<>(HttpCode.PARAM_INVALID.getCode(),
                    "邮箱与密码不能为空"
            );
        }

        // 根据邮箱查询密码
        UserAuth dbUserAuth = getOne(Wrappers.<UserAuth>lambdaQuery()
                .eq(UserAuth::getEmail, email)
        );
        if (dbUserAuth == null) {
            return new Response<>(HttpCode.DATA_EXIST.getCode(),
                    "邮箱对应账号不存在"
            );
        }


        // 比对密码
        String salt = dbUserAuth.getSalt();
        String md5Password = DigestUtil.md5Hex(password + salt);
        if (!md5Password.equals(dbUserAuth.getPassword())) {
            return new Response<>(HttpCode.LOGIN_PASSWORD_ERROR.getCode(),
                    "密码错误"
            );
        }

        // 返回响应
        Integer id = dbUserAuth.getId();
        return new Response<>(HttpCode.SUCCESS.getCode(),
                "登录成功",
                new LoginResponse() {{
                    setEmail(email);
                    setUserInfo(userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery()
                                    .eq(UserInfo::getId, id)
                            )
                    );
                    setToken(JwtUtil.getJwt(id));
                }}
        );
    }
}
