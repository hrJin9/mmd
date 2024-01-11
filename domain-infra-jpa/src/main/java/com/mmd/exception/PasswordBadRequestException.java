package com.mmd.exception;

public class PasswordBadRequestException extends DomainException {
    public PasswordBadRequestException(String message) {
        super(message);
    }

    public PasswordBadRequestException(Throwable cause) {
        super(cause);
    }

    public PasswordBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
