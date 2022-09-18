package com.study.til.design_pattern.decorator._02_after;

import com.study.til.design_pattern.decorator.Fighter;

public class LaserDecorator extends FighterDecorator {
    public LaserDecorator(Fighter decoratedFighter) {
        super(decoratedFighter);
    }

    @Override
    public void attack() {
        super.attack();
        System.out.println("레이저 발사");
    }
}
