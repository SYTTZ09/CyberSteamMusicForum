package com.syt.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.syt.model.common.dtos.res.Response;
import com.syt.model.user.dos.UserAuth;
import com.syt.model.user.dtos.req.LoginRequest;
import com.syt.model.user.dtos.res.LoginResponse;

public interface UserAuthService extends IService<UserAuth> {

    /**
     * 登录
     *
     * @param request
     * @return
     */
    public Response<LoginResponse> login(LoginRequest request);
}
