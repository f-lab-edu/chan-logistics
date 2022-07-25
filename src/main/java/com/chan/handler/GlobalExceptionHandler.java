package com.chan.handler;

import com.chan.common.Message;
import com.chan.common.StatusEnum;
import com.chan.exception.CenterFindFailedException;
import com.chan.exception.CenterValidationFailedException;
import com.chan.exception.InvoiceRequestValidationFailedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CenterFindFailedException.class)
    public ResponseEntity<Message> handleCustomerFindFailException(CenterFindFailedException ex){
        return responseBadRequest(ex.getMessage());
    }

    @ExceptionHandler(CenterValidationFailedException.class)
    public ResponseEntity<Message> handleCustomerFindFailException(CenterValidationFailedException ex){
        return responseBadRequest(ex.getMessage());
    }

    @ExceptionHandler(InvoiceRequestValidationFailedException.class)
    public ResponseEntity<Message> handleCustomerFindFailException(InvoiceRequestValidationFailedException ex){
        return responseBadRequest(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Message> handleException(Exception ex){
        return responseBadRequest(ex.getMessage());
    }

    private ResponseEntity responseBadRequest(String messages) {
        Message message = new Message();
        message.setStatus(StatusEnum.BAD_REQUEST);
        message.setMessage(messages);
        return ResponseEntity.badRequest().body(message);
    }

}
