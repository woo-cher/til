package com.study.til.observer._02_after;

public class Daughter implements Observer {

    @Override
    public void update(boolean isAngry) {
        if (isAngry) {
            System.out.println("딸 : 설거지 모드로 전환!");
        } else {
            System.out.println("딸 : 방탄 오빠들 봐야지");
        }
    }
}
