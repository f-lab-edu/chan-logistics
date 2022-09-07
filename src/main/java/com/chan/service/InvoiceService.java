package com.chan.service;

import com.chan.client.RiderClient;
import com.chan.common.SigunguCode;
import com.chan.domain.Address;
import com.chan.domain.Center;
import com.chan.domain.Invoice;
import com.chan.domain.OrderStatus;
import com.chan.dto.*;
import com.chan.exception.InvoiceFindFailedException;
import com.chan.exception.InvoiceRequestValidationFailedException;
import com.chan.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    private final CenterService centerService;

    private final SigunguCode sigunguCode;

    //private final RiderClient riderClient;

    @Transactional
    public void matchingInvoice(String localCode, List<InvoiceResponseDto> invoiceList){

        LocalDateTime nowTime = LocalDateTime.now();

        //라이더 정보 조회
        RiderResponseDto riderResponseDto = new RiderResponseDto();
        riderResponseDto.setLocalCode(localCode);
        riderResponseDto.setRiderId(1234L);

        //송장 정보 업데이트
        List<Invoice> invoiceStream = invoiceList.stream().map(invoiceRep -> {

            Invoice invoice = invoiceRepository
            .findByIdAndOrderStatus(invoiceRep.getInvoiceId(), OrderStatus.RECEPTION);
                if(invoice != null){
                    invoice.setOrderStatus(OrderStatus.MATCHING);
                    invoice.setMatchingCompletedTime(nowTime);
                    invoice.setRiderId(riderResponseDto.getRiderId());
                }
                return invoice;
            })
            .filter( out -> out!=null)
            .collect(Collectors.toList());

        if(invoiceStream.size() > 0){
            invoiceRepository.saveAll(invoiceStream);
        }

        //배송기사 정보 update
        //RiderMatchingDto riderMatchingDto = new RiderMatchingDto();
        //riderClient.matchDelivery(riderMatchingDto);

    }

    public Invoice findInvoice(Long id){

        Invoice invoice = invoiceRepository.findById(id).orElseThrow(InvoiceFindFailedException::new);

        return invoice;
    }

    public List<Invoice> findInvoice(String localCode, LocalDate date,OrderStatus orderStatus ,boolean meridiem){

        Center center = centerService.findCenter(localCode);

        return invoiceRepository.findAllByCenterAndOrderStatusAndDeliveryDateAndMeridiem(center, orderStatus, date, meridiem);
    }

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
        invoice.setMeridiem(invoiceDto.isMeridiem());
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
