package com.study.til.design_pattern.decorator._01_before;

import com.study.til.design_pattern.decorator.BasicFighter;
import com.study.til.design_pattern.decorator.Fighter;

public class Pilot {

    public static void main(String[] args) {
        Fighter fighter = new BasicFighter();
        fighter.attack();
        System.out.println("");

        fighter = new LaserFighter();
        fighter.attack();
        System.out.println("");

        fighter = new PlasmaFighter();
        fighter.attack();
        System.out.println("");

        fighter = new LaserPlasmaFighter();
        fighter.attack();
    }
}
