package com.chan.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Embeddable
@NoArgsConstructor @AllArgsConstructor
@Getter
public class Address {

    //도로명 주소
    @NotEmpty
    private String doroAddress;

    //한국 행정 구역 코드
    @NotNull
    private int sigunguCode;

    public void setDoroAddress(String doroAddress) {
        this.doroAddress = doroAddress;
    }

    public void setSigunguCode(int sigunguCode) {
        this.sigunguCode = sigunguCode;
    }
}
