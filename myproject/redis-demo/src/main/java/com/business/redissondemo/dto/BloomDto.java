package com.business.redissondemo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@EqualsAndHashCode
public class BloomDto implements Serializable {
    private Integer id;
    private  String msg;

    public BloomDto() {

    }

    public BloomDto(Integer id, String msg) {
        this.id = id;
        this.msg = msg;
    }
}
