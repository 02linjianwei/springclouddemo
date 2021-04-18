package com.business.rabbitmqdemo.common;

import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import java.io.Serializable;

@ToString
public class LoginEvent extends ApplicationEvent implements Serializable {

    public LoginEvent(Object source) {
        super(source);
    }
    private String userName;
    private String loginTime;
    private String ip;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public LoginEvent(Object source, String userName, String loginTime) {
        super(source);
        this.userName = userName;
        this.loginTime = loginTime;
        this.ip = ip;
    }
}
