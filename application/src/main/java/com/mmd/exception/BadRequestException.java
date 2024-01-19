package com.mmd.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends MmdApiException {
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    public BadRequestException(String message) {
        super(message);
    }
}
