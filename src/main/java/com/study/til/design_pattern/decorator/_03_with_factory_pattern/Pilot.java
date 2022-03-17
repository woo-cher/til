package com.study.til.design_pattern.decorator._03_with_factory_pattern;

public class Pilot {
    public static void main(String[] args) {
        FighterFactory factory = new FighterFactory();

        factory.getFighter(false, false, false).attack();
        System.out.println("");

        factory.getFighter(true, false, true).attack();
        System.out.println("");

        factory.getFighter(true, true, false).attack();
        System.out.println("");

        factory.getFighter(true, true, true).attack();
    }
}
