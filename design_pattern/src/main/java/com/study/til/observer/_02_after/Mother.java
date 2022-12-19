package com.study.til.observer._02_after;

public class Mother {

  public static void main(String[] args) {
    MotherSubject subject = new MotherSubject();

    subject.registerObserver(new Son());
    subject.registerObserver(new Daughter());
    subject.registerObserver(new Pomeranian());

    System.out.println("# 아빠가 화가 났다");
    subject.setAngry(true);
    subject.notifyObservers();

    System.out.println("\n# 아빠가 기분이 좋다");
    subject.setAngry(false);
    subject.notifyObservers();
  }
}
