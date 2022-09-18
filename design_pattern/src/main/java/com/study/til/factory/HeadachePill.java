package com.study.til.factory;

public class HeadachePill implements Medicine {

    @Override
    public void get() {
        System.out.println("두통약 드릴게요~");
    }
}
