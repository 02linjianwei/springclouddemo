package com.business.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString
public class RedPacketDto {
    private Integer userId;
    @NotNull
    private Integer total;
    @NotNull
    private Integer amount;
}
