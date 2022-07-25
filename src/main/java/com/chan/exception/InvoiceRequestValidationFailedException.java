package com.chan.exception;

public class InvoiceRequestValidationFailedException extends RuntimeException{
    public InvoiceRequestValidationFailedException() {
    }

    public InvoiceRequestValidationFailedException(String message) {
        super(message);
    }

    public InvoiceRequestValidationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
