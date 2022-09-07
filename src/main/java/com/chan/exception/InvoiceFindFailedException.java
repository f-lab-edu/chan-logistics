package com.chan.exception;

public class InvoiceFindFailedException extends RuntimeException{

    public InvoiceFindFailedException() {
    }

    public InvoiceFindFailedException(String message) {
        super(message);
    }

    public InvoiceFindFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
