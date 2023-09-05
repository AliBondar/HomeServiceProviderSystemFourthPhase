package org.example.service;

import org.springframework.mail.SimpleMailMessage;

public interface EmailSenderService {

    void sendEmail(String to, String emailMessage);

    void sendEmail(SimpleMailMessage email);

    SimpleMailMessage createEmail(String toEmail, String confirmationToken, String accountType);

}
