package com.chan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RiderMatchingRequestDto {

    private Long riderId;

    private LocalDate date;

    private boolean isPM;

    private List<RiderMatchingInvoiceDto> invoices;
}
