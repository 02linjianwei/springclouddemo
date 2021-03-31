package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
//@EnableEurekaClient
//@EnableDiscoveryClient
@EnableZuulProxy
@SpringBootApplication
public class ApplicationZuul {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationZuul.class, args);
    }
}
