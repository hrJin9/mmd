package com.mmd.exception;

import org.springframework.http.HttpStatus;

/* Jwt관련 exception 처리 */
public class TokenNotFoundException extends MmdApiException {
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    public TokenNotFoundException(String message) {
        super(message);
    }
}
