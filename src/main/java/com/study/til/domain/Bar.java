package com.study.til.domain;

import org.springframework.stereotype.Component;

@Component
public class Bar {
    public void print() {
        System.out.println(this.getClass());
    }
}
