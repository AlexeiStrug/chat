package com.example.registration.config;

import com.example.registration.model.User;
import com.example.registration.model.VerificationToken;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

/**
 * Created by Alex on 20.08.2017.
 */
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private String appUrl;
    private User user;

    public OnRegistrationCompleteEvent(
            User user, String appUrl) {
        super(user);

        this.user = user;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
