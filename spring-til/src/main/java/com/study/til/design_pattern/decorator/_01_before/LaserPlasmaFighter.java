package com.study.til.design_pattern.decorator._01_before;

import com.study.til.design_pattern.decorator.Fighter;

public class LaserPlasmaFighter implements Fighter {

    @Override
    public void attack() {
        System.out.println("탄환 발사");
        System.out.println("레이저 발사");
        System.out.println("플라즈마 발사");
    }
}
