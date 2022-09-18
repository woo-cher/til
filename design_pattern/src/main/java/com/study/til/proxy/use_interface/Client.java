package com.study.til.proxy.use_interface;

public class Client {

    public static void main(String[] args) {
        GameService gameService = new GameServiceProxy(new DefaultGameService());
        gameService.startGame();
    }
}
