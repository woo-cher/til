package com.study.til.singleton._02_synchronized_method;

public class GameSettings {

  private static volatile GameSettings instance;

  private GameSettings() {}

  // synchronized method over head
  public static synchronized GameSettings getInstance() {
    if (instance == null) {
      return new GameSettings();
    }

    return instance;
  }
}
