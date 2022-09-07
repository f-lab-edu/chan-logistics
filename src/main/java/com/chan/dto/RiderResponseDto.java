package com.chan.dto;

import com.chan.domain.Address;
import lombok.Data;

@Data
public class RiderResponseDto {

    Long riderId;
    String riderName;
    String localCode;
    Address address;
}
