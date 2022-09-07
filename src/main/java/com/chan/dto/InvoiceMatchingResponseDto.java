package com.chan.dto;

import lombok.Data;

import java.util.List;

@Data
public class InvoiceMatchingResponseDto {

    String localCode;

    List<InvoiceResponseDto> invoiceResponseDtoList;
}
