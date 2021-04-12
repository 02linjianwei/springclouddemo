package com.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class Person  {
    //private static final long serialVersionUID = 6394597178728332276l;
    private Long id;
    private String name;
    private String address;
//    private String tel;
//    private String servicePort;

    public Person() {

    }
    public Person(Long id, String name, String address) {
        this.id=id;
        this.name=name;
        this.address=address;
    }
}
