package com.example.ModelAgency_Spring.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sibApi.TransactionalEmailsApi;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailSender;
import sibModel.SendSmtpEmailTo;

import java.util.Collections;

@Service
public class SendinBlueService {

    private final TransactionalEmailsApi emailApi;

    @Value("${sendinblue.sender.email}")
    private String senderEmail;

    @Value("${sendinblue.sender.name}")
    private String senderName;

    public SendinBlueService(TransactionalEmailsApi emailApi) {
        this.emailApi = emailApi;
    }

    public String sendEmail(String toEmail, String subject, String body) {
        try {
            // Setting up the sender
            SendSmtpEmailSender sender = new SendSmtpEmailSender()
                    .email(senderEmail)
                    .name(senderName);

            // Setting up the recipient
            SendSmtpEmailTo recipient = new SendSmtpEmailTo()
                    .email(toEmail);

            // Constructing the email
            SendSmtpEmail email = new SendSmtpEmail()
                    .sender(sender)
                    .to(Collections.singletonList(recipient))
                    .subject(subject)
                    .htmlContent(body);

            // Sending the email
            emailApi.sendTransacEmail(email);

            return "Email sent successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send email: " + e.getMessage();
        }
    }
}
