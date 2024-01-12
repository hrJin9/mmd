package com.mmd.exception;

import org.springframework.http.HttpStatus;

public class TokenExpiredException extends AuthException {
    @Override
    public HttpStatus getStatus() {
        return super.getStatus();
    }

    public TokenExpiredException(String message) {
        super(message);
    }
}
