package com.mmd.exception;

import org.springframework.http.HttpStatus;

public class TokenNotValidException extends MmdApiException {
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    public TokenNotValidException(String message) {
        super(message);
    }
}
