package com.study.til.spring_core.domain;

public class Bar implements TilModel {
  public void print() {
    System.out.println(this.getClass());
  }

  @Override
  public String toString() {
    return this.getClass().toString();
  }
}
