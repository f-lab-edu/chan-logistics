package com.chan.dto;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class RiderMatchingDto {

    @NotEmpty
    private Long riderId;

    @NotEmpty
    private List<RiderMatchingInvoiceDto> invoices;
}
