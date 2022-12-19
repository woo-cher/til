package com.study.til.abstract_factory.foo_medicine;

import com.study.til.factory.Medicine;

public class FooHeadachePill implements Medicine {

  @Override
  public void get() {
    System.out.println("Foo 사 두통약 드릴게요~");
  }
}
