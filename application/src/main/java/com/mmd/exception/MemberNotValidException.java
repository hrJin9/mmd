package com.mmd.exception;

import org.springframework.http.HttpStatus;

public class MemberNotValidException extends MmdApiException {
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    public MemberNotValidException(String message) {
        super(message);
    }
}
