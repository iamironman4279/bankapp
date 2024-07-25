package com.bank;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailUtility {
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String SMTP_USER = "hemanth42079@gmail.com"; // Replace with your email
    private static final String SMTP_PASSWORD = "fzlhqqifncrmaxum"; // Replace with your email password

    public static void sendEmail(String toEmail, String subject, String messageBody) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USER, SMTP_PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SMTP_USER));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject);

        // Set email content type to HTML
        message.setContent(messageBody, "text/html; charset=utf-8");

        Transport.send(message);
    }
}
