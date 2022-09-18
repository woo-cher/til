package com.study.til.factory;

public class Cold implements Medicine {

    @Override
    public void get() {
        System.out.println("감기약 드릴게요~");
    }
}
