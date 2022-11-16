package com.imageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class ImageserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageserviceApplication.class, args);
    }

}
