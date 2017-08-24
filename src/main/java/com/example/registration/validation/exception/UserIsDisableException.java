package com.example.registration.validation.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by Alex on 24.08.2017.
 */
public class UserIsDisableException extends CustomException {
    @Override
    public int getCode() {
        return 403;
    }

    @Override
    public String getDescription() {
        return "User is disable!";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
