package com.example.demo.reference.errors;

import com.example.demo.reference.errors.core.CustomException;
import org.springframework.http.HttpStatus;

import java.util.Optional;

/**
 * Thrown during registration new user when the user with same username already exists.
 *
 */
public class UserWithSuchUsernameAlreadyExistsCustomException extends CustomException {
    @Override
    public int getCode() {
        return 409;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }

    @Override
    public String getDescription() {
        return "User with such username already exists";
    }

    @Override
    public Optional<String> getMessageKey() {
        return Optional.of("error.user.with.such.username.exists");
    }
}
