package com.business.balanceyw.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString
public class UserAccountDto {
    @NotNull
    private Long userId;
    @NotNull
    private Double amount;
}
