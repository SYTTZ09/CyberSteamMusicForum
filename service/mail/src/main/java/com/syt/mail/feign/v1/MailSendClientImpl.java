package com.syt.mail.feign.v1;

import com.syt.api.mail.MailSendClient;
import com.syt.mail.service.business.MailSendService;
import com.syt.model.mail.dtos.req.SendButtonMailRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@Api(value = "邮件发送服务", tags = "邮件发送服务")
@RequestMapping("/api/v1/send")
public class MailSendClientImpl implements MailSendClient {

    @Resource
    MailSendService mailSendService;

    @Override
    @PostMapping("/buttonMail")
    @ApiOperation("发送带按钮邮件")
    public int sendButtonMail(@RequestBody SendButtonMailRequest request) {
        return mailSendService.sendButtonMail(request.getTo(),
                request.getTitle(),
                request.getName(),
                request.getContent(),
                request.getUrl(),
                request.getButton()
        );
    }

}