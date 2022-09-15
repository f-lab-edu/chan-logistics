package com.chan.service;

import com.chan.DatabaseTest;
import com.chan.domain.Address;
import com.chan.domain.Center;
import com.chan.domain.Invoice;
import com.chan.domain.OrderStatus;
import com.chan.dto.CenterRequestDto;
import com.chan.dto.InvoiceAddRequestDto;
import com.chan.dto.MatchingInvoice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class InvoiceServiceTest extends DatabaseTest {

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    CenterService centerService;

    @Test
    @Order(1)
    void setupCenter(){
        addCenter();
    }

    @Test
    void requestInvoice() {

        Long orderId = 1L;
        LocalDate date = LocalDate.now().plusDays(1);
        boolean isPm = true;

        Address address = new Address();
        address.setDoroAddress("서울시 종로구");
        address.setSigunguCode(1111110);

        InvoiceAddRequestDto invoiceRequestDto = new InvoiceAddRequestDto();
        invoiceRequestDto.setDeliveryDate(date);
        invoiceRequestDto.setMeridiem(isPm);
        invoiceRequestDto.setStoreAddress(address);
        invoiceRequestDto.setCustomerAddress(address);
        invoiceRequestDto.setOrderId(orderId);

        Invoice invoice = invoiceService.requestInvoice(invoiceRequestDto);

        Assertions.assertEquals(invoice.getOrderId(), orderId);
        Assertions.assertEquals(invoice.getDeliveryDate(), date);
        Assertions.assertEquals(invoice.getOrderStatus(), OrderStatus.RECEPTION);
        Assertions.assertEquals(invoice.isMeridiem(), isPm);
    }

    @Test
    void updateInvoices() {

        Long riderId = 1L;
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);

        List<MatchingInvoice> invoiceList = new ArrayList<>();

        Invoice invoice = makeInvoice();
        invoiceList.add(new MatchingInvoice(invoice.getId()));

        List<Invoice> updateInvoiceList = invoiceService.updateInvoices(riderId, dateTime, invoiceList);


        for(Invoice updateInvoice : updateInvoiceList){
            Assertions.assertEquals(updateInvoice.getOrderStatus(), OrderStatus.MATCHING);
            Assertions.assertEquals(updateInvoice.getRiderId(), riderId);

        }
    }

    private Invoice makeInvoice(){

        Long orderId = 1L;
        LocalDate date = LocalDate.now().plusDays(1);
        boolean isPm = true;

        Address address = new Address();
        address.setDoroAddress("서울시 종로구");
        address.setSigunguCode(1111110);

        InvoiceAddRequestDto invoiceRequestDto = new InvoiceAddRequestDto();
        invoiceRequestDto.setDeliveryDate(date);
        invoiceRequestDto.setMeridiem(isPm);
        invoiceRequestDto.setStoreAddress(address);
        invoiceRequestDto.setCustomerAddress(address);
        invoiceRequestDto.setOrderId(orderId);

        return invoiceService.requestInvoice(invoiceRequestDto);
    }

    private Center addCenter(){
        String localCode = "A01";
        String centerName = "종로점";

        CenterRequestDto centerRequestDto = new CenterRequestDto();

        centerRequestDto.setLocalCode(localCode);
        centerRequestDto.setName(centerName);

        return centerService.addCenter(centerRequestDto.toEntity());
    }

}