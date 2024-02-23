package com.syt.model.user.dtos.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginRequest {
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;
    @ApiModelProperty(value = "密码", required = true)
    private String password;
}
