package com.chan.adaptor;

import com.chan.dto.InvoiceMatchingResponseDto;
import com.chan.service.InvoiceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class LogisticConsumer {

    private final InvoiceService invoiceService;

    private final ObjectMapper objectMapper;

    @SqsListener(value = "${app.sqs-queue-name}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveMatching(String jsonString, @Header("SenderId") String senderId) throws JsonProcessingException {

        log.info("receive Message :" + jsonString);

        InvoiceMatchingResponseDto invoiceMatching = objectMapper.readValue(jsonString, InvoiceMatchingResponseDto.class);
        invoiceService.matchingInvoice(invoiceMatching.getLocalCode(), invoiceMatching.getInvoiceResponseDtoList());
    }

}
