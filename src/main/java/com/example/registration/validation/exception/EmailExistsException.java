package com.example.registration.validation.exception;

/**
 * Created by Alex on 20.08.2017.
 */
@SuppressWarnings("serial")
public class EmailExistsException extends Throwable {

    public EmailExistsException(final String message) {
        super(message);
    }
}