package org.example.service.impl;

import jakarta.transaction.Transactional;
import org.example.service.EmailSenderService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;


@Service
@Transactional
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSenderImpl javaMailSenderImpl;
    private final JavaMailSender javaMailSender;


    public EmailSenderServiceImpl(JavaMailSenderImpl javaMailSenderImpl, JavaMailSender javaMailSender) {
        this.javaMailSenderImpl = javaMailSenderImpl;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(SimpleMailMessage email) {
        email.setFrom("ali.bondar2001@gmail.com");
        email.setSubject("Complete User Signup");
        javaMailSenderImpl.setHost("smtp.gmail.com");
        javaMailSenderImpl.setPort(587);
        javaMailSenderImpl.setUsername("ali.bondar2001@gmail.com");
        javaMailSenderImpl.setPassword("bqcmzkqakxzqzeop");
        Properties props = javaMailSenderImpl.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.debug", "true");
        javaMailSenderImpl.send(email);
    }

    @Override
    public SimpleMailMessage createEmail(String toEmail, String confirmationToken, String accountType) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setFrom("ali.bondar2001@gmail.com");
        if (accountType.equals("client")) {
            mailMessage.setSubject("Verify Your Email Address");
            mailMessage.setText("To continue the registration process, please click here : "
                    + "http://localhost:8081/signup/confirm?token=" + confirmationToken);
        } else if (accountType.equals("expert")) {
            mailMessage.setSubject("Verify Your Email Address");
            mailMessage.setText("To continue the registration process, please click here : "
                    + "http://localhost:8081/signup/confirm?token=" + confirmationToken);
        } else if (accountType.equals("admin")) {
            mailMessage.setSubject("Verify Your Email Address");
            mailMessage.setText("To continue the registration process, please click here : "
                    + "http://localhost:8081/signup/confirm?token=" + confirmationToken);
        }
        return mailMessage;
    }
}
