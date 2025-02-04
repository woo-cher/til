package com.study.til.abstract_factory.bar_medicine;

import com.study.til.factory.Medicine;

public class BarDigestive implements Medicine {

  @Override
  public void get() {
    System.out.println("Bar 사 소화제 드릴게요~");
  }
}
