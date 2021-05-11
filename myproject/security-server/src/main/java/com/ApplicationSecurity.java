package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@EnableAuthorizationServer
@EnableWebSecurity
@EnableDiscoveryClient
@SpringBootApplication
public class ApplicationSecurity {
    public static void main(String[] args) {
            SpringApplication.run(ApplicationSecurity.class, args);
        }
}
