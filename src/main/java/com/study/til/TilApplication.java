package com.study.til;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:env.properties")
public class TilApplication {

    public static void main(String[] args) {
        SpringApplication.run(TilApplication.class, args);
    }

}
