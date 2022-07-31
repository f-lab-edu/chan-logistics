package com.chan.domain;

import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
public class Invoice {

    @Id
    @GeneratedValue
    @Column(name = "invoice_id")
    private Long id;

    private Long orderId;

    private String invoiceCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id")
    private Center center;

    private LocalDate deliveryDate;

    private boolean meridiem;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="doroAddress", column=@Column(name = "store_doroAddress")),
            @AttributeOverride(name="sigunguCode", column=@Column(name = "store_sigunguCode"))
    })
    private Address storeAddress;

    private String storeLocalCode;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="doroAddress", column=@Column(name = "customer_doroAddress")),
            @AttributeOverride(name="sigunguCode", column=@Column(name = "customer_sigunguCode"))
    })
    private Address customerAddress;

    private String customerLocalCode;

    private LocalDateTime matchingCompletedTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public void setCenter(Center center) {
        this.center = center;
        center.getInvoiceList().add(this);
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setMeridiem(boolean meridiem) {
        this.meridiem = meridiem;
    }

    public void setStoreAddress(Address storeAddress) {
        this.storeAddress = storeAddress;
    }

    public void setCustomerAddress(Address customerAddress) {
        this.customerAddress = customerAddress;
    }

    public void setStoreLocalCode(String storeLocalCode) {
        this.storeLocalCode = storeLocalCode;
    }

    public void setCustomerLocalCode(String customerLocalCode) {
        this.customerLocalCode = customerLocalCode;
    }
}
