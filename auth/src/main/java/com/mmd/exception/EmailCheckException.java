package com.mmd.exception;

public class EmailCheckException extends AuthException {
    public EmailCheckException(String message) {
        super(message);
    }

    public EmailCheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailCheckException(Throwable cause) {
        super(cause);
    }
}
