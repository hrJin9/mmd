package com.todos.mmd.auth.exception;

import com.todos.mmd.global.exception.MmdException;

/* Jwt관련 exception 처리 */
public class AuthDeniedException extends MmdException {

    public AuthDeniedException(String message) {
        super(message);
    }

    public AuthDeniedException(Throwable cause) {
        super(cause);
    }

    public AuthDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

}
