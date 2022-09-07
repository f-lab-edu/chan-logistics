package com.chan.controller;

import com.chan.common.Message;
import com.chan.common.StatusEnum;
import com.chan.dto.InvoiceResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class HealthCheckController {

    @GetMapping("/")
    public ResponseEntity<Message> Hi(){
        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        return ResponseEntity.ok().body(message);
    }
}
