package com.business.rabbitmqdemo.loginlogyw.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
@Data
@ToString
public class UserLoginDto implements Serializable {
    @NotNull
    private String userName;
    @NotNull
    private  String password;
    private  long userId;

}
