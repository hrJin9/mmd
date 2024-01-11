package com.mmd.exception;

import org.springframework.http.HttpStatus;

public class PasswordBadRequestException extends MmdApiException {
    @Override
    public HttpStatus getStatus() {
        return null;
    }

    public PasswordBadRequestException(String message) {
        super(message);
    }
}
