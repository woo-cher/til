package com.study.til.factory;

public class Digestive implements Medicine {

    @Override
    public void get() {
        System.out.println("소화제 드릴게요~");
    }
}
