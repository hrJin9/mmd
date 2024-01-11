package com.mmd.exception;

import org.springframework.http.HttpStatus;

/* 애플리케이션 비즈니스 예외 */
public abstract class MmdApiException extends RuntimeException {
    public abstract HttpStatus getStatus();

    public MmdApiException(String message) {super(message);}
}
