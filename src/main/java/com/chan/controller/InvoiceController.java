package com.chan.controller;

import com.chan.common.Message;
import com.chan.common.StatusEnum;
import com.chan.domain.Invoice;
import com.chan.dto.InvoiceFindRequestDto;
import com.chan.dto.InvoiceRequestDto;
import com.chan.dto.InvoiceResponseDto;
import com.chan.exception.InvoiceRequestValidationFailedException;
import com.chan.service.InvoiceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/logistics/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;

    private final ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<Message> findInvoice(@RequestBody @Valid InvoiceFindRequestDto invoiceFindRequestDto, Errors errors) throws JsonProcessingException {
        Message message = new Message();

        if (errors.hasErrors()) {
            throw new InvoiceRequestValidationFailedException(objectMapper.writeValueAsString(errors));
        }

        List<Invoice> invoiceList = invoiceService.findInvoice(invoiceFindRequestDto.getLocalCode(), invoiceFindRequestDto.getDate(), invoiceFindRequestDto.isMeridiem());

        message.setStatus(StatusEnum.OK);
        message.setMessage("송장 추가 성공");
        message.setData(invoiceList.stream().map(InvoiceResponseDto::new));

        return ResponseEntity.ok().body(message);
    }

    @PostMapping
    public ResponseEntity<Message> requestInvoice(@RequestBody @Valid InvoiceRequestDto invoiceRequestDto, Errors errors) throws JsonProcessingException {

        Message message = new Message();

        if (errors.hasErrors()) {
            throw new InvoiceRequestValidationFailedException(objectMapper.writeValueAsString(errors));
        }

        Invoice invoice = invoiceService.requestInvoice(invoiceRequestDto);

        message.setStatus(StatusEnum.OK);
        message.setMessage("송장 추가 성공");
        message.setData(new InvoiceResponseDto(invoice));

        return ResponseEntity.ok().body(message);
    }
}
