package com.chan.dto;

import lombok.*;

import java.time.LocalDate;

@Data
public class RiderRequestDto {
    LocalDate date;
    String localCode;
    boolean pm;
}
