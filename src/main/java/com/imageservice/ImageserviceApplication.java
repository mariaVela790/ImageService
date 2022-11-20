package com.imageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


//@EnableJpaRepositories(basePackages = "com.imageservice.repository")

@SpringBootApplication
public class ImageserviceApplication {

    public static void main(String[] args) {


        SpringApplication.run(ImageserviceApplication.class, args);
    }

}
