package com.chan.dto;

import com.chan.domain.Center;
import lombok.Data;

@Data
public class CenterRequestDto {

    private String name;

    private String localCode;

    public Center toEntity() {
        Center center = new Center(this.name, this.localCode);
        return center;
    }
}
