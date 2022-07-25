package com.chan.dto;

import com.chan.domain.Address;
import com.chan.domain.Invoice;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class InvoiceResponseDto {

    private Long orderId;

    private LocalDate deliveryDate;

    private String invoiceCode;

    private boolean meridiem; //0: am 1: pm

    private Address storeAddress;

    private String storeLocalCode;

    private Address customerAddress;

    private String customerLocalCode;

    public InvoiceResponseDto(Invoice invoice){

        this.orderId = invoice.getOrderId();
        this.invoiceCode = invoice.getInvoiceCode();
        this.deliveryDate = invoice.getDeliveryDate();
        this.meridiem = invoice.isMeridien();
        this.storeAddress = invoice.getStoreAddress();
        this.storeLocalCode = invoice.getStoreLocalCode();
        this.customerAddress = invoice.getCustomerAddress();
        this.customerLocalCode = invoice.getCustomerLocalCode();

    }
}
