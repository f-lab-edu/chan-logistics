package com.chan.client;

import com.chan.common.Message;
import com.chan.dto.RiderMatchingRequestDto;
import com.chan.dto.RiderRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="rider", url="${rider.ribbon.listOfServers}")
public interface RiderClient {

    @GetMapping("/rider/delivery")
    Message getRider(@RequestBody RiderRequestDto riderRequestDto);

    @PostMapping("/rider")
    Message matchDelivery(@RequestBody RiderMatchingRequestDto workRequestDto);

}
