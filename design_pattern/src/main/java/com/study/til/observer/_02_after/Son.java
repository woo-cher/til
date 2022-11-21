package com.study.til.observer._02_after;

public class Son implements Observer {

  @Override
  public void update(boolean isAngry) {
    if (isAngry) {
      System.out.println("아들 : 공부 모드로 전환!");
    } else {
      System.out.println("아들 : 롤 한판 해볼까");
    }
  }
}
