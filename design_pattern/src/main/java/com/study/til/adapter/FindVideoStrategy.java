package com.study.til.adapter;

public class FindVideoStrategy implements FindStrategy {

  @Override
  public void find(boolean global) {
    System.out.println((global ? "글로벌 " : "") + "동영상 컨텐츠 검색");
  }
}
