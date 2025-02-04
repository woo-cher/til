package com.study.til.observer._01_before;

public class Mother {

  public static void main(String[] args) {
    Son son = new Son();
    Daughter daughter = new Daughter();

    System.out.println("# 아빠가 화가 났다");
    son.act(true);
    daughter.act(true);

    System.out.println("\n# 아빠가 기분이 좋다");
    son.act(false);
    daughter.act(false);
  }
}
