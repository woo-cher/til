package com.study.til.proxy.use_extends;

import com.study.til.proxy.before.GameService;

public class Client {

  public static void main(String[] args) {
    GameService gameService = new GameServiceChildProxy();
    gameService.startGame();
  }
}
