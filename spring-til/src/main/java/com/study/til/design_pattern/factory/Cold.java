package com.study.til.design_pattern.factory;

public class Cold implements Medicine {

    @Override
    public void get() {
        System.out.println("감기약 드릴게요~");
    }
}
