package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@MapperScan("com.business.redyw.mapper")
@SpringBootApplication
@EnableAsync
public class ApplicationForRedis {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationForRedis.class, args);
    }
}
