package com.chan.service;

import com.chan.client.RiderClient;
import com.chan.common.Message;
import com.chan.common.SigunguCode;
import com.chan.domain.Address;
import com.chan.domain.Center;
import com.chan.domain.Invoice;
import com.chan.domain.OrderStatus;
import com.chan.dto.*;
import com.chan.exception.InvoiceFindFailedException;
import com.chan.exception.InvoiceMatchingFailedException;
import com.chan.exception.InvoiceRequestValidationFailedException;
import com.chan.repository.InvoiceRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    private final CenterService centerService;

    private final SigunguCode sigunguCode;

    private final RiderClient riderClient;

    private final ObjectMapper objectMapper;

    @Transactional
    public void matchingInvoice(String localCode, boolean isPm , List<MatchingInvoice> invoiceList) throws JsonProcessingException {

        LocalDateTime nowTime = LocalDateTime.now();

        //라이더 정보 요청
        RiderResponseDto riderResponseDto = getRider(localCode,nowTime.toLocalDate(),isPm);

        //송장 정보 업데이트
        updateInvoices(riderResponseDto.getId(), nowTime, invoiceList);

        //배송기사 송장 정보 업데이트 요청
        updateMatchingRider(riderResponseDto.getId(), nowTime.toLocalDate(), isPm, invoiceList);

    }

    public List<Invoice> findInvoice(String localCode, LocalDate date,OrderStatus orderStatus ,boolean meridiem){

        Center center = centerService.findCenter(localCode);

        return invoiceRepository.findAllByCenterAndOrderStatusAndDeliveryDateAndMeridiem(center, orderStatus, date, meridiem);
    }

    @Transactional
    public Invoice requestInvoice(InvoiceAddRequestDto invoiceDto){

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

    public List<Invoice> updateInvoices(Long riderId, LocalDateTime nowTime, List<MatchingInvoice> invoiceList){

        List<Invoice> invoiceStream = invoiceList.stream().map(invoiceRep -> {

                    Invoice invoice = invoiceRepository
                        .findByIdAndOrderStatus(invoiceRep.getInvoiceId(), OrderStatus.RECEPTION);

                    if(invoice != null){
                        invoice.setOrderStatus(OrderStatus.MATCHING);
                        invoice.setMatchingCompletedTime(nowTime);
                        invoice.setRiderId(riderId);
                    }
                    return invoice;
            })
            .filter( out -> out!=null)
            .collect(Collectors.toList());

        if(invoiceStream.size() > 0){
            return invoiceRepository.saveAll(invoiceStream);
        }
        else{
            throw new InvoiceMatchingFailedException("invoice list의 크기가 0입니다.");
        }

    }

    private RiderResponseDto updateMatchingRider(Long riderId, LocalDate date, boolean isPm, List<MatchingInvoice> matchingInvoiceList) throws JsonProcessingException {

        RiderMatchingRequestDto riderMatchingRequestDto = new RiderMatchingRequestDto();
        riderMatchingRequestDto.setRiderId(riderId);
        riderMatchingRequestDto.setPM(isPm);
        riderMatchingRequestDto.setDate(date);

        //SQS에서 받아온 MatchingInvoice List를 Rider Service 요청에 받는 데이터 형태로 변경
        riderMatchingRequestDto.setInvoices(
                matchingInvoiceList.stream().map( responseInvoice ->{
                    Invoice invoice = invoiceRepository.findById(responseInvoice.getInvoiceId()).orElseThrow(InvoiceMatchingFailedException::new);
                    return new RiderMatchingInvoiceDto(invoice);
                }).collect(Collectors.toList())
        );

        Message responseMessage = riderClient.matchDelivery(riderMatchingRequestDto);

        if(responseMessage.isOk()){
            RiderResponseDto riderResponseDto = objectMapper.readValue(responseMessage.getData().toString(), RiderResponseDto.class);
            return riderResponseDto;
        }
        else{
            throw new InvoiceMatchingFailedException("매칭 중 라이더 매칭이 실패하였습니다.");
        }

    }

    private RiderResponseDto getRider(String localCode, LocalDate date, boolean isPm) throws JsonProcessingException {

        RiderRequestDto riderRequestDto = new RiderRequestDto();
        riderRequestDto.setDate(date);
        riderRequestDto.setLocalCode(localCode);
        riderRequestDto.setPm(isPm);

        Message responseMessage = riderClient.getRider(riderRequestDto);

        if(responseMessage.isOk()){
            RiderResponseDto riderResponseDto = objectMapper.readValue(responseMessage.getData().toString(), RiderResponseDto.class);
            return riderResponseDto;
        }
        else{
            throw new InvoiceMatchingFailedException("매칭 중 라이더 요청이 실패하였습니다.");
        }

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
