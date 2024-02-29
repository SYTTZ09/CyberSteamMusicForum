package com.syt.user.service.business;

import com.syt.model.common.dtos.res.Response;
import com.syt.model.user.dtos.req.LoginRequest;
import com.syt.model.user.dtos.req.RegisterRequest;
import com.syt.model.user.dtos.res.ActivateResponse;
import com.syt.model.user.dtos.res.LoginResponse;
import com.syt.model.user.dtos.res.RegisterResponse;

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
    Response<RegisterResponse> register(RegisterRequest request);

    /**
     * 激活
     * @param email
     * @param token
     * @return
     */
    Response<ActivateResponse> activate(String email, String token);
}
