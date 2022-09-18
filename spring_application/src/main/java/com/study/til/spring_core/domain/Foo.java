package com.study.til.spring_core.domain;

public class Foo implements TilModel {
    private final Bar bar;

    public Foo(Bar bar) {
        this.bar = bar;
    }

    public void doSomething() {
        this.bar.print();
    }
}
