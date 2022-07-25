package com.chan.service;

import com.chan.common.SigunguCode;
import com.chan.domain.Address;
import com.chan.domain.Center;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class InvoiceServiceTest {

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    SigunguCode sigunguCode;

    @Test
    public void sigunguCodeCheck() throws Exception{

        Address addressTrue = new Address("테스트 도로명", 1111110);
        Address addressFalse = new Address("테스트 도로명", 1111111);

        boolean deliveryTrue = sigunguCode.isDelivery(addressTrue);
        boolean deliveryFalse = sigunguCode.isDelivery(addressFalse);

        String localCode = sigunguCode.getLocalCode(addressTrue);

        Assertions.assertEquals(deliveryTrue, true);
        Assertions.assertEquals(deliveryFalse, false);
        Assertions.assertEquals(localCode, "A01");


    }

}