package com.example.cygnimashup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class CygniMashupApplication {

    public static void main(String[] args) {
        SpringApplication.run(CygniMashupApplication.class, args);
    }

}
