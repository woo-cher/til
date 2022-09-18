package com.study.til.design_pattern.decorator._02_after;

import com.study.til.design_pattern.decorator.Fighter;

public class MissileDecorator extends FighterDecorator {
    public MissileDecorator(Fighter decoratedFighter) {
        super(decoratedFighter);
    }

    @Override
    public void attack() {
        super.attack();
        System.out.println("미사일 발사");
    }
}
