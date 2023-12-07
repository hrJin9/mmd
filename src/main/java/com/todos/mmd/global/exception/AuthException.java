package com.todos.mmd.global.exception;

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
