package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.EnableZipkinServer;

@EnableZipkinServer
@SpringBootApplication
public class ApplicationZipkin {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationZipkin.class, args);
    }
}
