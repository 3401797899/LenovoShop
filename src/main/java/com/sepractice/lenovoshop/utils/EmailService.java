package com.sepractice.lenovoshop.utils;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SendEmailUtil {
    @Autowired
    private static JavaMailSender javaMailSender;

    public static void sendEmail(String to, String subject, String content) {
        // 发送邮件
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setText(content);
        message.setTo(to);
        message.setSentDate(new Date());
        javaMailSender.send(message);
    }
}
