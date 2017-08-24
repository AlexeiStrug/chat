package com.example.registration.validation.exception;

import org.springframework.http.HttpStatus;

/**
 * Thrown when requested user entity by username is not found.
 *
 */
public class NoUserWithSuchUsernameCustomException extends CustomException {
    @Override
    public int getCode() {
        return 404;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getDescription() {
        return "No user with such username";
    }

}
