package com.chan.controller;

import com.chan.common.Message;
import com.chan.common.StatusEnum;
import com.chan.domain.Center;
import com.chan.dto.CenterRequestDto;
import com.chan.dto.CenterResponseDto;
import com.chan.exception.CenterValidationFailedException;
import com.chan.exception.InvoiceRequestValidationFailedException;
import com.chan.service.CenterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/logistics/center")
public class CenterController {

    private final CenterService centerService;

    private final ObjectMapper objectMapper;

    @GetMapping("/{localCode}")
    public ResponseEntity<Message> findCenter(@PathVariable String localCode){

        Message message = new Message();

        Center customer = centerService.findCenter(localCode);

        message.setStatus(StatusEnum.OK);
        message.setMessage("조회 성공");
        message.setData(new CenterResponseDto(customer));

        return ResponseEntity.ok().body(message);
    }

    @PostMapping
    public ResponseEntity<Message> addCenter(@RequestBody @Valid CenterRequestDto centerRequestDto, Errors errors) throws JsonProcessingException {

        Message message = new Message();

        if (errors.hasErrors()) {
            throw new CenterValidationFailedException(objectMapper.writeValueAsString(errors));
        }

        Center center = centerService.addCenter(centerRequestDto.toEntity());

        message.setStatus(StatusEnum.OK);
        message.setMessage("센터 추가 성공");
        message.setData(new CenterResponseDto(center));

        return ResponseEntity.ok().body(message);
    }


}
