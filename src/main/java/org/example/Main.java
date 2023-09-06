package org.example;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws MessagingException, IOException {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "imap.gmail.com");
        prop.put("mail.smtp.port", "993");
        prop.put("mail.smtp.ssl.trust", "*");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("ali.bondar2001@gmail.com", "bqcmzkqakxzqzeop");
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("ali.bondar2001@gmail.com"));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse("pedadashii@gmail.com"));
        message.setSubject("Mail Subject");

        String msg = "This is my first email using JavaMailer";

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);

        MimeBodyPart attachmentBodyPart = new MimeBodyPart();
        attachmentBodyPart.attachFile(new File("path/to/file"));

        multipart.addBodyPart(attachmentBodyPart);

        String msgStyled = "This is my <b style='color:red;'>bold-red email</b> using JavaMailer";


    }
}
