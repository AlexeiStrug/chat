package com.example.registration.validation.exception;

import org.springframework.http.HttpStatus;

import java.util.Optional;

/**
 * {@code CustomException} is supper class for exceptions to be thrown.
 * Contains all required information
 * for analyzing error from server response.
 *
 */
public abstract class CustomException extends RuntimeException {
    private Object[] descriptionArgs;
    private Object[] messageArgs = null;

    /**
     * @return formatted description with argument
     * @see CustomException#setDescriptionArgs
     */
    public final String getInterpolatedDescription() {
        return String.format(getDescription(), descriptionArgs);
    }

    /**
     * @param descriptionArgs arguments for description formatting
     */
    public final void setDescriptionArgs(Object... descriptionArgs) {
        this.descriptionArgs = descriptionArgs;
    }

    /**
     * @return arguments for user localized message
     */
    public Object[] getMessageArgs() {
        return messageArgs;
    }

    /**
     * @param messageArgs arguments for user localized message
     */
    public final void setMessageArgs(Object... messageArgs) {
        this.messageArgs = messageArgs;
    }

    /**
     * @return unique error code
     */
    public abstract int getCode();

    /**
     * @return message key to retrieve from {@code MessageSource}
     */
    public Optional<String> getMessageKey() {
        return Optional.empty();
    }

    /**
     * @return description of exception
     */
    public abstract String getDescription();

    /**
     * @return appropriate HTTP status code
     */
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
