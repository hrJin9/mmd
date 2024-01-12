package com.mmd.exception;

import org.springframework.http.HttpStatus;

public class TokenNotValidException extends AuthException {
    @Override
    public HttpStatus getStatus() {
        return super.getStatus();
    }

    public TokenNotValidException(String message) {
        super(message);
    }
}
