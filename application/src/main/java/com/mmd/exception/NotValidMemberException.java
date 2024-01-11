package com.mmd.exception;

public class NotValidMemberException extends BusinessException {
    public NotValidMemberException(String message) {
        super(message);
    }

    public NotValidMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotValidMemberException(Throwable cause) {
        super(cause);
    }
}
