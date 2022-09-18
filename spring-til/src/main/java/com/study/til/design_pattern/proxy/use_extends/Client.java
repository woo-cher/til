package com.study.til.design_pattern.proxy.use_extends;

import com.study.til.design_pattern.proxy.before.GameService;

public class Client {

    public static void main(String[] args) {
        GameService gameService = new GameServiceChildProxy();
        gameService.startGame();
    }
}
