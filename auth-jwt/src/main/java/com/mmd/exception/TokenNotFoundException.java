package com.mmd.exception;

import org.springframework.http.HttpStatus;

/* Jwt관련 exception 처리 */
public class TokenNotFoundException extends AuthException {
    @Override
    public HttpStatus getStatus() {
        return super.getStatus();
    }

    public TokenNotFoundException(String message) {
        super(message);
    }
}
