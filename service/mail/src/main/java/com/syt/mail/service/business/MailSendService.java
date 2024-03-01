package com.syt.mail.service.business;

public interface MailSendService {

    int sendButtonMail(String to, String title, String name, String content, String url, String button);
}
