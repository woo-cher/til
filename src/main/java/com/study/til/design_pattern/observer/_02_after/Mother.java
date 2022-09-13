package com.study.til.design_pattern.observer._02_after;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Mother {

    public static void main(String[] args) {
        MotherSubject subject = new MotherSubject();

        subject.registerObserver(new Son());
        subject.registerObserver(new Daughter());
        subject.registerObserver(new Pomeranian());

        log.info("# 아빠가 화가 났다");
        subject.setAngry(true);
        subject.notifyObservers();

        log.info("\n# 아빠가 기분이 좋다");
        subject.setAngry(false);
        subject.notifyObservers();
    }
}
