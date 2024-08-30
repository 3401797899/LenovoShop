package com.sepractice.lenovoshop.utils;

import jakarta.annotation.Resource;
import jakarta.mail.internet.InternetAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@Component
public class EmailService{
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String content) {
        // 发送邮件
        SimpleMailMessage message = new SimpleMailMessage();
        try {
            message.setFrom(String.valueOf(new InternetAddress("3401797899@qq.com", "LenovoShop", "UTF-8")));
        }catch (Exception e){
            e.printStackTrace();
        }
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        javaMailSender.send(message);
    }
}
