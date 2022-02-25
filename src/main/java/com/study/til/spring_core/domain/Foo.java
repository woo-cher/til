package com.study.til.spring_core.domain;

import org.springframework.stereotype.Service;

@Service
public class Foo {
    private final Bar bar;

    public Foo(Bar bar) {
        this.bar = bar;
    }

    public void doSomething() {
        this.bar.print();
    }
}
