package com.chan.dto;

import com.chan.domain.Address;
import com.chan.domain.Invoice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RiderMatchingInvoiceDto {

    private Long invoiceId;

    private String invoiceCode;

    private LocalDate deliveryDate;

    private Address storeAddress;

    private String storeLocalCode;

    private Address customerAddress;

    private String customerLocalCode;

    public RiderMatchingInvoiceDto(Invoice invoice) {
        this.invoiceId = invoice.getId();
        this.invoiceCode = invoice.getInvoiceCode();
        this.deliveryDate = invoice.getDeliveryDate();
        this.storeAddress = invoice.getStoreAddress();
        this.storeLocalCode = invoice.getStoreLocalCode();
        this.customerAddress = invoice.getCustomerAddress();
        this.customerLocalCode = invoice.getCustomerLocalCode();
    }


}
