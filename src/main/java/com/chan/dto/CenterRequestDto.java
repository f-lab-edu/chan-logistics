package com.chan.dto;

import com.chan.domain.Center;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CenterRequestDto {

    @NotEmpty
    private String name;

    @NotEmpty
    private String localCode;

    public Center toEntity() {
        Center center = new Center(this.name, this.localCode);
        return center;
    }
}
