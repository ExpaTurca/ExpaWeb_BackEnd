package com.expastudios.blogweb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {
    @Value("${spring.mail.username}")
    String from;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;

    public void sendMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage(); message.setSubject(subject); message.setTo(to);
        message.setText(body); message.setFrom(from); System.out.println(from); javaMailSender.send(message);
    }

    @Async
    public void sendHtmlMail(String to, String subject, String templateName, Context context) throws MessagingException {
        MimeMessage mail = javaMailSender.createMimeMessage();
        String body = templateEngine.process(templateName, context);
        MimeMessageHelper helper = new MimeMessageHelper(mail, true); helper.setFrom(from); helper.setTo(to);
        helper.setSubject(subject); helper.setText(body, true); javaMailSender.send(mail);
    }
}
