package com.chan.dto;

import com.chan.domain.Address;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class InvoiceAddRequestDto {

    @NotNull
    private Long orderId;

    @NotNull
    private LocalDate deliveryDate;

    @NotNull
    private boolean meridiem; //0: am 1: pm

    @NotNull @Valid
    private Address storeAddress;

    @NotNull @Valid
    private Address customerAddress;

}
