package com.study.til.spring_core.domain;

import org.springframework.stereotype.Component;

@Component
public class Bar {
    public void print() {
        System.out.println(this.getClass());
    }
}
