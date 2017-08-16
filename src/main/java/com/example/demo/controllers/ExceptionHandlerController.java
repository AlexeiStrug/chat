package com.example.demo.controllers;

import com.example.demo.reference.errors.core.AccessDeniedCustomException;
import com.example.demo.reference.errors.core.CustomException;
import com.example.demo.reference.errors.core.ErrorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandlerController {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseEntity<ErrorInfo> handleErrorCodeException(CustomException e) {

        String message = e.getMessageKey().isPresent()
                ? messageSource.getMessage(e.getMessageKey().get(), e.getMessageArgs(), LocaleContextHolder.getLocale())
                : null;

        ErrorInfo dto = new ErrorInfo(e.getCode(), message, e.getInterpolatedDescription());

        return new ResponseEntity<>(dto, e.getHttpStatus());
    }


    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<ErrorInfo> handleException(AccessDeniedException e) {
        AccessDeniedCustomException error = new AccessDeniedCustomException();

        String message = error.getMessageKey().isPresent()
                ? messageSource.getMessage(error.getMessageKey().get(), null, LocaleContextHolder.getLocale()) : null;
        ErrorInfo dto = new ErrorInfo(error.getCode(), message, e.getMessage());

        return new ResponseEntity<>(dto, error.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Exception handleException(Exception e) {
       return e;
    }
}
