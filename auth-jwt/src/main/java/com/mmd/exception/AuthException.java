package com.mmd.exception;

import org.springframework.http.HttpStatus;

public class AuthException extends MmdApiException {
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    public AuthException(String message) {
        super(message);
    }
}
