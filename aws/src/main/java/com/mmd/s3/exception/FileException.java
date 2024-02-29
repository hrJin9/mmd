package com.mmd.s3.exception;

import org.springframework.http.HttpStatus;

public class FileException extends MmdApiException {
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public FileException(String message) {
        super(message);
    }
}
