package com.aushev.autoriasearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AutoRiaSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoRiaSearchApplication.class, args);
    }

}
