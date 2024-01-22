package com.mmd.exception;

import org.springframework.http.HttpStatus;

public class EmailNotFoundException extends MmdApiException {
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    public EmailNotFoundException(String message) {
        super(message);
    }
}
