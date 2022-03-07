package com.study.til.design_pattern.proxy.before;

public class Client {

    /**
     * Client : "I need time watch of GameService.startGame()"
     */
    public static void main(String[] args) {
        GameService gameService = new GameService();

        // start time watch
        gameService.startGame();
        // end time watch
    }
}
