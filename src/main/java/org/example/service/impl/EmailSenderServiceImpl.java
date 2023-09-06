package org.example.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.example.exception.SendEmailFailedException;
import org.example.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailSenderServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(String to, String emailMessage) {
        System.out.println("sendEmail1+++++++++++++++++++++++++++++++++++++++++++");
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(emailMessage, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("bondarali1380@gmail.com");
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new SendEmailFailedException("Failed to send email for: " + emailMessage);
        }
    }

    @Override
    public void sendEmail(SimpleMailMessage email) {
        System.out.println("sendEmail2++++++++++++++++++++");
        javaMailSender.send(email);
    }

    @Override
    @Async
    public SimpleMailMessage createEmail(String toEmail, String confirmationToken, String accountType) {
        System.out.println("createEmail+++++++++++++++++++++++++++++++++++++++++");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setFrom("bondarali1380@gmail.com");
        if (accountType.equals("client")) {
            mailMessage.setSubject("Complete Client signup.");
            mailMessage.setText("To confirm your account, please click here : "
                    + "http://localhost:8081/signup/client-signup/confirm?token=" + confirmationToken);
        } else if (accountType.equals("expert")) {
            mailMessage.setSubject("Complete Expert signup.");
            mailMessage.setText("To confirm your account, please click here : "
                    + "http://localhost:8081/signup/expert-signup/confirm?token=" + confirmationToken);
        }
        System.out.println("createEmailBeforeReturn+++++++++++++++++++++++++++++");
        return mailMessage;
    }
}
