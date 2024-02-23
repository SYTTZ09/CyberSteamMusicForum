package com.syt.user.controller.v1;

import com.syt.model.common.dtos.res.Response;
import com.syt.model.user.dtos.req.LoginRequest;
import com.syt.model.user.dtos.res.LoginResponse;
import com.syt.user.service.UserAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/login")
@Api(value = "用户登录", tags = "用户登录")
public class LoginController {

    @Resource
    private UserAuthService userAuthService;

    @PostMapping("/login_auth")
    @ApiOperation("用户登录")
    public Response<LoginResponse> login(@RequestBody LoginRequest request) {
        return userAuthService.login(request);
    }
}
