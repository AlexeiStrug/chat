package com.example.demo.reference.errors;

import com.example.demo.reference.errors.core.ForbiddenCustomException;

/**
 * Created by Alex on 14.08.2017.
 */
public class UserCouldNotWriteHereException extends ForbiddenCustomException {
    @Override
    public String getDescription() {
        return "User can't write here!";
    }
}
