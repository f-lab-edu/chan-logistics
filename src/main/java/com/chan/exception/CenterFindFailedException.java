package com.chan.exception;

public class CenterFindFailedException extends RuntimeException{

    public CenterFindFailedException() {
    }

    public CenterFindFailedException(String message) {
        super(message);
    }

    public CenterFindFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
