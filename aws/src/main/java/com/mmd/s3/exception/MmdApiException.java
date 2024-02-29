package com.mmd.s3.exception;

import org.springframework.http.HttpStatus;

/* aws 비즈니스 예외 */
public abstract class MmdApiException extends RuntimeException {
    public abstract HttpStatus getStatus();

    public MmdApiException(String message) {super(message);}
}
