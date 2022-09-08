package com.chan.handler;

import com.chan.common.Message;
import com.chan.common.StatusEnum;
import com.chan.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CenterFindFailedException.class)
    public ResponseEntity<Message> handleException(CenterFindFailedException ex){
        return responseBadRequest(ex.getMessage());
    }

    @ExceptionHandler(CenterValidationFailedException.class)
    public ResponseEntity<Message> handleException(CenterValidationFailedException ex){
        return responseBadRequest(ex.getMessage());
    }

    @ExceptionHandler(InvoiceRequestValidationFailedException.class)
    public ResponseEntity<Message> handleException(InvoiceRequestValidationFailedException ex){
        return responseBadRequest(ex.getMessage());
    }

    @ExceptionHandler(InvoiceFindFailedException.class)
    public ResponseEntity<Message> handleException(InvoiceFindFailedException ex){
        return responseBadRequest(ex.getMessage());
    }

    @ExceptionHandler(InvoiceMatchingFailedException.class)
    public ResponseEntity<Message> handleException(InvoiceMatchingFailedException ex){
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
        log.error(messages);
        return ResponseEntity.badRequest().body(message);
    }

}
