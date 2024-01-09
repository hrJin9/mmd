package com.mmd.exception;

public class NotValidTokenException extends AuthException {
    public NotValidTokenException(String message) {
        super(message);
    }

    public NotValidTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotValidTokenException(Throwable cause) {
        super(cause);
    }
}
