package com.chan.service;

import com.chan.DatabaseTest;
import com.chan.domain.Center;
import com.chan.dto.CenterRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CenterServiceTest extends DatabaseTest {

    @Autowired
    CenterService centerService;

    @Test
    void addCenterAndFindCenter() {

        String localCode = "A01";
        String centerName = "센터이름";

        CenterRequestDto centerRequestDto = new CenterRequestDto();

        centerRequestDto.setLocalCode(localCode);
        centerRequestDto.setName(centerName);

        centerService.addCenter(centerRequestDto.toEntity());

        Center findCenter = centerService.findCenter(localCode);

        Assertions.assertEquals(findCenter.getLocalCode(), localCode);
        Assertions.assertEquals(findCenter.getName(), centerName);
    }

}