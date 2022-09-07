package com.chan.dto;

import com.chan.domain.OrderStatus;
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
    OrderStatus status;

    @NotNull
    boolean meridiem;
}

