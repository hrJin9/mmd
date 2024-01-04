package com.todos.mmd.auth.exception;

import com.todos.mmd.global.exception.MmdException;

/* Jwt관련 exception 처리 */
public class JwtException extends MmdException {

    public JwtException(String message) {
        super(message);
    }

    public JwtException(Throwable cause) {
        super(cause);
    }

    public JwtException(String message, Throwable cause) {
        super(message, cause);
    }

}
