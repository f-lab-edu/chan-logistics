package com.chan.client;

import com.chan.common.Message;
import com.chan.dto.RiderMatchingDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="rider", url="${rider.ribbon.listOfServers}")
public interface RiderClient {

    @PutMapping("/rider")
    Message matchDelivery(@RequestBody RiderMatchingDto workRequestDto);
}
