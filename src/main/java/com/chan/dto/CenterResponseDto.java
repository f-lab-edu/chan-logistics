package com.chan.dto;

import com.chan.domain.Center;
import lombok.Data;

@Data
public class CenterResponseDto {

    private String name;

    private String localCode;

    public CenterResponseDto(Center center){
        this.name = center.getName();
        this.localCode = center.getLocalCode();
    }

}
