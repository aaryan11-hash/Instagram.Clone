package com.aaryan.Instagram.Clone.Services;

import com.aaryan.Instagram.Clone.Domain.RealTime.User;
import com.aaryan.Instagram.Clone.Domain.RealTime.UserTag;
import com.aaryan.Instagram.Clone.Repository.DomainRelated.UserRepository;
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

    private final UserRepository userRepository;


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

    @Async
    public void sendNotificationToTaggedUser(UserTag userTagged, User userWhoPosted ,Long postId){

        User taggeduserObject = userRepository.getByAccountSettings_Username(userTagged.getUserWhoWasTagged());

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(environment.getProperty("server.adminMailSender.email")));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(taggeduserObject.getAccountSettings().getEmail()));
            message.setSubject("New Post Tag Email");
            message.setText("Dear Customer,"
                    + "\n\n You were tagged by"+userWhoPosted.getAccountSettings().getUsername()
                    + "\n\n" + "click this link to go to the post {this part will redirect to the front end where userId of the user who posted and the postId to which the mailed user was tagged in will be sent..}");


            Transport.send(message);



        } catch (MessagingException e) {
            e.printStackTrace();


        }
    }

    @Async
    public void sendPasswordChangeNotification(User user,String oldPassword,String newPassword){


        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(environment.getProperty("server.adminMailSender.email")));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(taggeduserObject.getAccountSettings().getEmail()));
            message.setSubject("Password Change");
            message.setText("Dear Customer,"
                    + "\n\n You recently changed your password from"+oldPassword+" to: "+newPassword
                    //todo  :::: the given link endpoints have to be created ::::
                    //todo  :::: second database has to be created for the saving of the temp password change that happens in such transactions ::::
                    + "\nplease confirm your action by clicking this link : http://localhost/account/setting/{"+user.getUserId()+"}/confirmAction"
                    + "\nnot you? please click this link : http://localhost/account/setting/{"+user.getUserId()+"}/revertAction");

            Transport.send(message);



        } catch (MessagingException e) {
            e.printStackTrace();


        }

    }
}
