package com.study.til.proxy.use_interface;

public class DefaultGameService implements GameService {

    @Override
    public void startGame() {
          System.out.println("Welcome game!");
    }
}
