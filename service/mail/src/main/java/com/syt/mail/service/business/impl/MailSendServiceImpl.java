package com.syt.mail.service.business.impl;

import com.syt.mail.service.business.MailSendService;
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
public class MailSendServiceImpl implements MailSendService {

    @Resource
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    private static final String htmlStart = "<!DOCTYPE html>" + "<html lang=\"zh-CN\">";
    private static final String htmlEnd = "</html>";
    private static final String headStart = "<head>" +
            "<meta charset=\"UTF-8\">" +
            "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">";
    private static final String headEnd = "</head>";
    private static final String titleStart = "<title>";
    private static final String titleEnd = "</title>";
    private static final String style = "<style>" +
            "        * {" +
            "            margin: 0;" +
            "            padding: 0;" +
            "            box-sizing: border-box;" +
            "            text-align: center;" +
            "        }" +
            "        body," +
            "        html {" +
            "            margin: 20px 0;" +
            "            background-color: #f9f9f9;" +
            "        }" +
            "        .container {" +
            "            background: #fff;" +
            "            background-color: #fff;" +
            "            border: 1px #eee solid;" +
            "            border-radius: 8px;" +
            "            margin: 20px auto;" +
            "            max-width: 520px;" +
            "            min-height: 290px;" +
            "            padding: 10px 30px;" +
            "        }" +
            "        .title .text {" +
            "            font-size: 50px;" +
            "            font-weight: 900;" +
            "            color: #000;" +
            "            transition: all 0.5s;" +
            "        }" +
            "        .title .point {" +
            "            font-size: 70px;" +
            "            font-weight: 900;" +
            "            color: #b5699e;" +
            "            transition: all 0.5s;" +
            "        }" +
            "        .content {" +
            "            background: #fff;" +
            "            background-color: #fff;" +
            "            border: 1px #eee solid;" +
            "            border-radius: 8px;" +
            "            margin: 20px auto 20px;" +
            "            max-width: 520px;" +
            "            min-height: 200px;" +
            "            padding: 20px;" +
            "        }" +
            "        .content p {" +
            "            color: #353535;" +
            "            text-align: left;" +
            "            padding: 0 0 20px;" +
            "        }" +
            "        .tip {" +
            "            color: #888888;" +
            "            font-size: 12px;" +
            "            text-align: center;" +
            "            margin: 10px;" +
            "        }" +
            "        a {" +
            "            background: #b5699e;" +
            "            border-radius: 4px;" +
            "            color: #fff;" +
            "            cursor: pointer;" +
            "            display: inline-block;" +
            "            height: 36px;" +
            "            line-height: 36px;" +
            "            min-width: 100px;" +
            "            padding: 0 28px;" +
            "            text-align: center;" +
            "            text-decoration: none;" +
            "            font-weight: 600;" +
            "            margin: 20px auto;" +
            "        }" +
            "    </style>";
    private static final String bodyStart = "<div class=\"title\">" +
            "        <span class=\"text\">CyberSteam</span>" +
            "        <span class=\"point\">.</span>" +
            "    </div>" +
            "    <div class=\"container\">" +
            "        <div class=\"content\">";
    private static final String bodyEnd = "</div>" +
            "        <div class=\"tip\">CyberSteam 团队</div>" +
            "    </div>" +
            "</body>";
    private static final String pStart = "<p>";
    private static final String pEnd = "</p>";
    private static final String aHrefStart = "<a href=\"";
    private static final String aHrefEnd = "\">";
    private static final String aEnd = "</a>";

    /**
     * 发送带按钮的邮件
     * @param to
     * @param title
     * @param name
     * @param content
     * @param url
     * @param button
     * @return 0 发送成功   1 发送失败
     */
    public int sendButtonMail(String to, String title, String name, String content, String url, String button) {
        // 邮件对象
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String text = htmlStart + headStart + titleStart + title +
                titleEnd + style + headEnd + bodyStart + pStart + "Hi，" + name +
                pEnd + pStart + content +
                pEnd + aHrefStart + url +
                aHrefEnd + button +
                aEnd + bodyEnd + htmlEnd;
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
            // 邮件主题
            message.setSubject(title);
            // 邮件发送方
            message.setFrom(from);
            // 邮件接受方
            message.setTo(to);
            // 发送日期
            message.setSentDate(new Date());
            // 邮件正文
            message.setText(text, true);
        } catch (MessagingException e) {
            log.error(e.getMessage());
            return 1;
        }
        // 发送
        javaMailSender.send(mimeMessage);
        return 0;
    }

}
