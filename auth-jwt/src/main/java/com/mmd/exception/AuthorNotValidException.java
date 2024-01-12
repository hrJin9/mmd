package com.mmd.exception;

import org.springframework.http.HttpStatus;

public class AuthorNotValidException extends AuthException {
    @Override
    public HttpStatus getStatus() {
        return super.getStatus();
    }

    public AuthorNotValidException(String message) {
        super(message);
    }
}
