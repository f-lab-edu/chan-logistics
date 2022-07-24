package com.chan.service;

import com.chan.domain.Center;
import com.chan.repository.CenterRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CenterServiceTest {

    @Autowired
    CenterService centerService;

    @Autowired
    CenterRepository centerRepository;

    @Test
    public void centerAdd() throws Exception{
        Center center = new Center("test 센터", "A01");

        Center center1 = centerService.addCenter(center);
        Center findCenter = centerService.findCenter(center1.getLocalCode());

        Assertions.assertEquals(center.getLocalCode(), findCenter.getLocalCode());
        Assertions.assertEquals(center.getName(), findCenter.getName());

    }
}