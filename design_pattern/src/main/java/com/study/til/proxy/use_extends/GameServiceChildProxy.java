package com.study.til.proxy.use_extends;

import com.study.til.proxy.before.GameService;

/**
 * If GameService class has "final" property?
 * Proxy can't extend `GameService` and can't use `lazy-init`
 */
public class GameServiceChildProxy extends GameService {

    @Override
    public void startGame() {
        long startTime = System.currentTimeMillis();
        super.startGame();
        System.out.println(System.currentTimeMillis() - startTime);
    }
}
