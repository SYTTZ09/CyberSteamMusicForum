package com.syt.user.controller.v1;

import com.syt.model.common.dtos.res.Response;
import com.syt.model.user.dtos.req.LoginRequest;
import com.syt.model.user.dtos.req.RegisterRequest;
import com.syt.model.user.dtos.res.LoginResponse;
import com.syt.user.service.business.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/account")
@Api(value = "用户账号服务", tags = "用户账号服务")
public class AccountController {

    @Resource
    private AccountService accountService;

    @PostMapping("/login")
    @ApiOperation("登录")
    public Response<LoginResponse> login(@RequestBody LoginRequest request) {
        return accountService.login(request);
    }

    @PostMapping("/register")
    @ApiOperation("注册")
    public Response<String> register(@RequestBody RegisterRequest request) {
        return accountService.register(request);
    }

    @GetMapping("/activate")
    @ApiOperation("激活")
    public String activate(@RequestParam String email, @RequestParam String token) {
        return accountService.activate(email, token).getMessage();
    }


}
