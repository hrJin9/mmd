package com.mmd.exception;

/* 비즈니스 로직 예외 */
public class BusinessException extends CommonException {
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }
}
