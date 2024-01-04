package com.todos.mmd.auth.exception;

import com.todos.mmd.global.exception.MmdException;

public class ExpiredRefreshTokenException extends MmdException {

    public ExpiredRefreshTokenException(String message) {
        super(message);
    }

    public ExpiredRefreshTokenException(Throwable cause) {
        super(cause);
    }

    public ExpiredRefreshTokenException(String message, Throwable cause) {
        super(message, cause);
    }

}
