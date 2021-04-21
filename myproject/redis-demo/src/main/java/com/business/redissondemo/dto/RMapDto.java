package com.business.redissondemo.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class RMapDto implements Serializable {
    private Integer id;
    private String name;

    public RMapDto() {

    }

    public RMapDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
