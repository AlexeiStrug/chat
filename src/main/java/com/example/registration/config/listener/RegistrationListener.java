package com.example.registration.config.listener;

import com.example.registration.config.OnRegistrationCompleteEvent;
import com.example.registration.model.User;
import com.example.registration.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

/**
 * Created by Alex on 20.08.2017.
 */
@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private IUserService service;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {

        try {
            this.confirmRegistration(event);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private boolean confirmRegistration(OnRegistrationCompleteEvent event) throws MessagingException {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl
                = event.getAppUrl() + "/regitrationConfirm?token=" + token;

        String html = "You registered successfully. We will send you a confirmation message to your email account \n<a href='" + confirmationUrl + "'>confirmation</a>";

        MimeMessage message = mailSender.createMimeMessage();

        message.setSubject(subject);
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(recipientAddress);
        helper.setText(html, true);
        mailSender.send(message);

        return true;
    }
}
