package com.study.til.design_pattern.decorator._03_with_factory_pattern;

import com.study.til.design_pattern.decorator.BasicFighter;
import com.study.til.design_pattern.decorator.Fighter;
import com.study.til.design_pattern.decorator._02_after.LaserDecorator;
import com.study.til.design_pattern.decorator._02_after.MissileDecorator;
import com.study.til.design_pattern.decorator._02_after.PlasmaDecorator;

public class FighterFactory {
    public Fighter getFighter(boolean laser, boolean missile, boolean plasma) {
        Fighter fighter = new BasicFighter();

        if (laser) fighter = new LaserDecorator(fighter);
        if (missile) fighter = new MissileDecorator(fighter);
        if (plasma) fighter = new PlasmaDecorator(fighter);

        return fighter;
    }
}
