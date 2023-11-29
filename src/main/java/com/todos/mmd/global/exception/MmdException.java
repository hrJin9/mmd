package com.todos.mmd.global.exception;

/* 전역 예외 */
public class MmdException extends RuntimeException {

    public MmdException(String message) {
        super(message);
    }

    public MmdException(Throwable cause) {
        super(cause);
    }

    public MmdException(String message, Throwable cause) {
        super(message, cause);
    }

}
