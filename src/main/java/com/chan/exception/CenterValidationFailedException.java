package com.chan.exception;

public class CenterValidationFailedException extends RuntimeException{
    public CenterValidationFailedException() {
    }

    public CenterValidationFailedException(String message) {
        super(message);
    }

    public CenterValidationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
