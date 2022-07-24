package com.chan.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter @NoArgsConstructor
public class Center {

    @Id @GeneratedValue
    @Column(name = "center_id")
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Column(unique = true)
    private String localCode;

    public Center(String name, String localCode){
        this.name = name;
        this.localCode = localCode;
    }


}
