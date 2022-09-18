package com.study.til.spring_core.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String hello() {
        log.info("hello() handler processing");
        return "Hello spring-til";
    }

    @GetMapping("/specific-url-pattern")
    public String specificUrlPattern() {
        log.info("specificUrlPattern() handler processing");
        return "Test sepecific url pattern filter !!";
    }
}
