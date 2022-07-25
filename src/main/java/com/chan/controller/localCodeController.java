package com.chan.controller;

import com.chan.common.Message;
import com.chan.common.SigunguCode;
import com.chan.common.StatusEnum;
import com.chan.domain.Center;
import com.chan.dto.CenterResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/logistics/localCode")
public class localCodeController {

    private final SigunguCode sigunguCode;

    @GetMapping("/{sigungu}")
    public ResponseEntity<Message> findCenter(@PathVariable String sigungu){

        Message message = new Message();

        if(sigunguCode.isDelivery(sigungu)){
            String localCode = sigunguCode.getLocalCode(sigungu);
            message.setStatus(StatusEnum.OK);
            message.setMessage("Local Code 조회 성공");
            message.setData(localCode);
        }
        else{
            message.setStatus(StatusEnum.OK);
            message.setMessage("일치 하는 주소가 없습니다.");
        }

        return ResponseEntity.ok().body(message);
    }
}
