package com.syt.model.mail.dtos.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SendButtonMailRequest {
    @ApiModelProperty(value = "接收方邮箱", required = true)
    private String to;

    @ApiModelProperty(value = "邮件标题", required = true)
    private String title;

    @ApiModelProperty(value = "收件人姓名", required = true)
    private String name;

    @ApiModelProperty(value = "邮件内容", required = true)
    private String content;

    @ApiModelProperty(value = "按钮URL", required = true)
    private String url;

    @ApiModelProperty(value = "按钮提示", required = true)
    private String button;
}
