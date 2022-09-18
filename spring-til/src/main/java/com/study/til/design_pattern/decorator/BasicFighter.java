package com.study.til.design_pattern.decorator;

import com.study.til.design_pattern.decorator.Fighter;

public class BasicFighter implements Fighter {

    @Override
    public void attack() {
        System.out.println("탄환 발사");
    }
}
