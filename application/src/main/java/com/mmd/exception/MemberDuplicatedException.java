package com.mmd.exception;

import org.springframework.http.HttpStatus;

public class MemberDuplicatedException extends MmdApiException {
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public MemberDuplicatedException(String message) {
        super(message);
    }
}
