package com.chan.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "center")
    private List<Invoice> invoiceList = new ArrayList<>();


    public Center(String name, String localCode){
        this.name = name;
        this.localCode = localCode;
    }


}
