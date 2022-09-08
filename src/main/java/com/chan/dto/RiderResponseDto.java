package com.chan.dto;

import com.chan.domain.Address;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class RiderResponseDto {

    private Long id;

    private String accountId;

    private String name;

    private String telephone;

    private Address address;
}
