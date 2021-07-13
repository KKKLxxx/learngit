package com.example.demo.Service;

import com.example.demo.Utils.Verify;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@Transactional
public class VerifyService extends UserService
{

    @Value("${spring.mail.username}")
    private String sender;
    @Autowired
    private JavaMailSender mailSender;
    private LocalDateTime SendTime;
    private String AuthCode = null;

    Logger logger;

    public Boolean SendAuthCode(String receiver)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        SendTime = LocalDateTime.now();
        AuthCode = Verify.generateVerCode();
        message.setFrom(sender);
        message.setTo(receiver);
        message.setSubject("【XX APP验证码】");
        message.setText("您好：" + receiver + "\n您收到的验证码是" + AuthCode);
        mailSender.send(message);
        return true;
    }

    public boolean codeExist()
    {
        return this.AuthCode != null;
    }

    public boolean ifValid()
    {
        LocalDateTime SubTime = LocalDateTime.now();
        Duration duration = Duration.between(this.SendTime, SubTime);
        return duration.toMinutes() <= 5;
    }

    public boolean ifEqual(String Code)
    {
        return Code.equals(AuthCode);
    }
}
