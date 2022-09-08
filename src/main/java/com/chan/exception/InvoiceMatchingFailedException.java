package com.chan.exception;

public class InvoiceMatchingFailedException extends RuntimeException{

    public InvoiceMatchingFailedException() {
    }

    public InvoiceMatchingFailedException(String message) {
        super(message);
    }

    public InvoiceMatchingFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
