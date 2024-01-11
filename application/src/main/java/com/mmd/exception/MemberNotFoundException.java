package com.mmd.exception;

import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends MmdApiException {
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    public MemberNotFoundException(String message) {
        super(message);
    }
}
