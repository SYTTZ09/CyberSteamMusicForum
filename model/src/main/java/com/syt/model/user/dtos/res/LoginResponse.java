package com.syt.model.user.dtos.res;

import com.syt.model.user.dos.UserInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginResponse {
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;
    @ApiModelProperty(value = "用户信息", required = true)
    private UserInfo userInfo;
    @ApiModelProperty(value = "密钥", required = true)
    private String token;
}
