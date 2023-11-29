package com.todos.mmd.global.exception;

public class PasswordBadRequestException extends BadRequestException {
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
