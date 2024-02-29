package com.mmd.exception;

import org.springframework.http.HttpStatus;

/* OAuth2관련 exception 처리 */
public class OAuth2Exception extends MmdApiException {
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    public OAuth2Exception(String message) {
        super(message);
    }
}
