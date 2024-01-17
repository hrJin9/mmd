package com.mmd.exception;

import org.springframework.http.HttpStatus;

public class ContentsNotFoundException extends MmdApiException {
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    public ContentsNotFoundException(String message) {
        super(message);
    }
}
