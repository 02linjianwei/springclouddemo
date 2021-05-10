package com;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
@EnableWebSecurity
@EnableAuthorizationServer
@SpringBootApplication
public class ApplicationSecurity {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ApplicationSecurity.class).web(true).run(args);
    }
}
