package com.mmd.exception;

import org.springframework.http.HttpStatus;

public class TokenExpiredException extends MmdApiException {
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
    public TokenExpiredException(String message) {
        super(message);
    }
}
