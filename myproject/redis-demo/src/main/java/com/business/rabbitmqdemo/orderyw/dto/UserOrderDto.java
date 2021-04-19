package com.business.rabbitmqdemo.orderyw.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
@Data
@ToString
public class UserOrderDto implements Serializable {
    @NotNull
    private String orderNo;
    @NotNull
    private long userId;
}
