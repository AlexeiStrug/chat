package com.example.demo.reference.errors;

import com.example.demo.reference.errors.core.CustomException;
import org.springframework.http.HttpStatus;

/**
 * Thrown when requested user entity by username is not found.
 *
 * @author Igor Rybak
 */
public class NoUserWithSuchUsernameCustomException extends CustomException {
    @Override
    public int getCode() {
        return 2;
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