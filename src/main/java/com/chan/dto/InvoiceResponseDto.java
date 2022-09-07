package com.chan.dto;

import com.chan.domain.Address;
import com.chan.domain.Invoice;
import com.chan.domain.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class InvoiceResponseDto {

    private Long invoiceId;

    private Long orderId;

    private LocalDate deliveryDate;

    private String invoiceCode;

    private OrderStatus orderStatus;

    private Long riderId;

    private boolean meridiem; //0: am 1: pm

    private Address storeAddress;

    private String storeLocalCode;

    private Address customerAddress;

    private String customerLocalCode;

    public InvoiceResponseDto(Invoice invoice){

        this.invoiceId = invoice.getId();
        this.orderId = invoice.getOrderId();
        this.invoiceCode = invoice.getInvoiceCode();
        this.deliveryDate = invoice.getDeliveryDate();
        this.meridiem = invoice.isMeridiem();
        this.storeAddress = invoice.getStoreAddress();
        this.storeLocalCode = invoice.getStoreLocalCode();
        this.customerAddress = invoice.getCustomerAddress();
        this.customerLocalCode = invoice.getCustomerLocalCode();
        this.orderStatus = invoice.getOrderStatus();
        this.riderId = invoice.getRiderId();
    }

}
