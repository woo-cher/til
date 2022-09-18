package com.study.til.design_pattern.decorator._02_after;

import com.study.til.design_pattern.decorator.Fighter;
import com.study.til.design_pattern.decorator.BasicFighter;

public class Pilot {

    public static void main(String[] args) {
        // 기본 전투기
        Fighter fighter = new BasicFighter();
        fighter.attack();

        System.out.println("");

        // 레이저만 획득
        fighter = new LaserDecorator(new BasicFighter());
        fighter.attack();

        System.out.println("");

        // 플라즈마만 획득
        fighter = new PlasmaDecorator(new BasicFighter());
        fighter.attack();

        System.out.println("");

        // 모두 획득
        fighter = new PlasmaDecorator(new LaserDecorator(new MissileDecorator(new BasicFighter())));
        fighter.attack();
    }
}
