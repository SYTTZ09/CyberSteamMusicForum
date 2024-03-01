package com.syt.api.mail;

import com.syt.model.mail.dtos.req.SendButtonMailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("cyber-steam-music-mail")
@RequestMapping("/api/v1/send")
public interface MailSendClient {
    @PostMapping("/buttonMail")
    int sendButtonMail(@RequestBody SendButtonMailRequest request);
}
