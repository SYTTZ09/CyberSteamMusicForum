package com.syt.user.service.business;

import com.syt.model.common.dtos.res.Response;
import com.syt.model.user.dtos.req.LoginRequest;
import com.syt.model.user.dtos.req.RegisterRequest;
import com.syt.model.user.dtos.req.ForgetPasswordRequest;
import com.syt.model.user.dtos.res.LoginResponse;

public interface AccountService {

    /**
     * 登录
     *
     * @param request
     * @return
     */
    Response<LoginResponse> login(LoginRequest request);


    /**
     * 注册
     *
     * @param request
     * @return
     */
    Response<String> register(RegisterRequest request);

    /**
     * 激活
     * @param email
     * @param token
     * @return
     */
    Response<String> activate(String email, String token);

    /**
     * 忘记密码 发起重设密码申请
     * @param request
     * @return
     */
    Response<String> forgetPassword(ForgetPasswordRequest request);

    /**
     * 重设密码
     * @param email
     * @param token
     * @return
     */
    Response<String> resetPassword(String email, String password, String token);

    /**
     * 刷新 token
     * @return
     */
    Response<String> isLogin();
}
