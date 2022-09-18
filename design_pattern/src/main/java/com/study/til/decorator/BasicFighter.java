package com.study.til.decorator;

public class BasicFighter implements Fighter {

    @Override
    public void attack() {
        System.out.println("탄환 발사");
    }
}
