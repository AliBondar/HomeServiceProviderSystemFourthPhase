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

//    @Override
//    public void sendEmail(String to, String emailMessage) {
//        System.out.println("sendEmail1+++++++++++++++++++++++++++++++++++++++++++");
//        try {
//            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
//            helper.setText(emailMessage, true);
//            helper.setTo(to);
//            helper.setSubject("Confirm your email");
//            helper.setFrom("ali.bondar2001@gmail.com");
//            System.out.println("into the tyr +++++++++++");
//            javaMailSender.send(mimeMessage);
//            System.out.println("email sent ++++++++++++++++++++++++++++++++++++");
//        } catch (MessagingException e) {
//            System.out.println("EMAIL DIDNT SENT ++++++++++++++++++++++++++++");
//            throw new SendEmailFailedException("Failed to send email for: " + emailMessage);
//        }
//    }

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
            mailMessage.setSubject("Complete Client signup.");
            mailMessage.setText("To confirm your account, please click here : "
                    + "http://localhost:8081/signup/confirm?token=" + confirmationToken);
        } else if (accountType.equals("expert")) {
            mailMessage.setSubject("Complete Expert signup.");
            mailMessage.setText("To confirm your account, please click here : "
                    + "http://localhost:8081/signup/confirm?token=" + confirmationToken);
        } else if (accountType.equals("admin")) {
            mailMessage.setSubject("Complete Admin signup.");
            mailMessage.setText("To confirm your account, please click here : "
                    + "http://localhost:8081/signup/confirm?token=" + confirmationToken);
        }
        return mailMessage;
    }
}
