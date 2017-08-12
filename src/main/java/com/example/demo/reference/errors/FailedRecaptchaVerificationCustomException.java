package com.example.demo.reference.errors;


import com.example.demo.reference.errors.core.CustomException;

/**
 * Thrown when failed recaptcha response verification.
 *
 * @author Igor Rybak
 */
public class FailedRecaptchaVerificationCustomException extends CustomException {
    @Override
    public int getCode() {
        return 3;
    }

    @Override
    public String getDescription() {
        return "Failed recaptcha verification";
    }
}
