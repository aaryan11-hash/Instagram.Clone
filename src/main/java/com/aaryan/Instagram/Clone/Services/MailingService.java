package com.aaryan.Instagram.Clone.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MailingService {

    private final Session session;
    private final Environment environment;


    @Async
    public void sendAuthEmail(String to) {

        String tempToken = UUID.randomUUID().toString();
        String token = UUID.randomUUID().toString();

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(environment.getProperty("server.adminMailSender.email")));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject("Confirmation Email");
            message.setText("Dear Customer,"
                    + "\n\n Please use the given link to verify this is your email please!"
                    + "\n\n" + "http://localhost:8081/api/Auth/accountVerification/"+token);


            Transport.send(message);



        } catch (MessagingException e) {
            e.printStackTrace();


        }

    }
}
