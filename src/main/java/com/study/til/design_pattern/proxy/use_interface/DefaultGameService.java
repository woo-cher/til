package com.study.til.design_pattern.proxy.use_interface;

public class DefaultGameService implements GameService {

    @Override
    public void startGame() {
          System.out.println("Welcome game!");
    }
}
