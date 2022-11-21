package com.study.til.observer._02_after;

public class Pomeranian implements Observer {

  @Override
  public void update(boolean isAngry) {
    if (isAngry) {
      System.out.println("포메 : 똥 오줌을 참고 애교를 발사한다");
    } else {
      System.out.println("포메 : 똥을 싼다");
    }
  }
}
