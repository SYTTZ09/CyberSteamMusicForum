package com.syt.user.service.business.impl;

import com.syt.user.service.business.ActivateMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Service
@Transactional
@Slf4j
public class ActivateMailServiceImpl implements ActivateMailService {

    @Resource
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendActivateMail(String to, String code) throws MessagingException {
        // 邮件对象
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
            // 邮件主题
            message.setSubject("蒸汽赛博 - 验证码");
            // 邮件发送方
            message.setFrom(from);
            // 邮件接受方
            message.setTo(to);
            // 发送日期
            message.setSentDate(new Date());
            // 邮件正文
            String text = code;
            message.setText(text, true);
        } catch (MessagingException e) {
            throw new MessagingException();
        }
        // 发送
        javaMailSender.send(mimeMessage);
    }

}
