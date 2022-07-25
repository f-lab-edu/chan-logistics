package com.chan.service;

import com.chan.common.SigunguCode;
import com.chan.domain.Address;
import com.chan.domain.Center;
import com.chan.domain.Invoice;
import com.chan.domain.OrderStatus;
import com.chan.dto.InvoiceRequestDto;
import com.chan.exception.InvoiceRequestValidationFailedException;
import com.chan.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    private final CenterService centerService;

    private final SigunguCode sigunguCode;

    @Transactional
    public Invoice requestInvoice(InvoiceRequestDto invoiceDto){

        //check delivery
        checkDelivery(invoiceDto.getStoreAddress(), invoiceDto.getStoreAddress());

        String storeLocalCode = sigunguCode.getLocalCode(invoiceDto.getStoreAddress());
        String customerLocalCode = sigunguCode.getLocalCode(invoiceDto.getCustomerAddress());

        //지역코드가 일치하지않으면 배달 지원 x
        if(!storeLocalCode.equals(customerLocalCode)){
            throw new InvoiceRequestValidationFailedException("현재 동일 지역 배달만 지원합니다.");
        }

        Center center = centerService.findCenter(storeLocalCode);

        Invoice invoice = new Invoice();

        invoice.setOrderId(invoiceDto.getOrderId());
        invoice.setInvoiceCode(makeUniqueString());
        invoice.setCenter(center);
        invoice.setMeridien(invoiceDto.isMeridiem());
        invoice.setDeliveryDate(invoiceDto.getDeliveryDate());
        invoice.setStoreAddress(invoiceDto.getStoreAddress());
        invoice.setCustomerAddress(invoiceDto.getCustomerAddress());
        invoice.setStoreLocalCode(storeLocalCode);
        invoice.setCustomerLocalCode(customerLocalCode);
        invoice.setOrderStatus(OrderStatus.RECEPTION);

        Invoice saveInvoice = invoiceRepository.save(invoice);

        return saveInvoice;
    }

    private boolean checkDelivery(Address store, Address customer){

        if(!sigunguCode.isDelivery(store) || !sigunguCode.isDelivery(customer) ){
            throw new InvoiceRequestValidationFailedException("배달을 지원하지 않는 지역입니다.");
        }

        return true;
    }

    private String makeUniqueString(){

        LocalDateTime now = LocalDateTime.now();

        String dateTime = now.format(DateTimeFormatter.ofPattern("yyMMddHHmmssSSS"));
        String genString = RandomStringUtils.random(10,true, true);

        return dateTime + "_" + genString;
    }

}
