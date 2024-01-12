package com.mmd.exception;

import org.springframework.http.HttpStatus;

public class EmailNotFoundException extends AuthException {
    @Override
    public HttpStatus getStatus() {
        return super.getStatus();
    }

    public EmailNotFoundException(String message) {
        super(message);
    }
}
