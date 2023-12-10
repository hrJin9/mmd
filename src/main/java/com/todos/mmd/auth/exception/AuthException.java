package com.todos.mmd.auth.exception;

import com.todos.mmd.global.exception.MmdException;

/* auth관련 exception 처리 */
public class AuthException extends MmdException {

    public AuthException(String message) {
        super(message);
    }

    public AuthException(Throwable cause) {
        super(cause);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

}
