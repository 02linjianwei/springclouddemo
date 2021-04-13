package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.business.mapper")
@SpringBootApplication
public class ApplicationForRedis {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationForRedis.class, args);
    }
}
