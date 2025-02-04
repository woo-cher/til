package com.study.til.observer._01_before;

public class Son {

  public void act(boolean isAngry) {
    if (isAngry) {
      System.out.println("아들 : 공부 모드로 전환!");
    } else {
      System.out.println("아들 : 롤 한판 해볼까");
    }
  }
}
