package com.chan.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class InvoiceFindRequestDto {

    @NotEmpty
    String localCode;

    @NotNull
    LocalDate date;

    @NotNull
    boolean meridiem;
}

