package com.mmd.exception;

import org.springframework.http.HttpStatus;

public class AuthorNotValidException extends MmdApiException {
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    public AuthorNotValidException(String message) {
        super(message);
    }
}
