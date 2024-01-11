package com.mmd.exception;

public class MemberDuplicatedException extends BusinessException {
    public MemberDuplicatedException(String message) {
        super(message);
    }

    public MemberDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberDuplicatedException(Throwable cause) {
        super(cause);
    }
}
