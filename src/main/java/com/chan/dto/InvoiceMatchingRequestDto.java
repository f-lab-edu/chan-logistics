package com.chan.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @NoArgsConstructor
public class InvoiceMatchingRequestDto {
    String localCode;
    LocalDate date;
    boolean isPm;
}
