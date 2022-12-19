package com.study.til.spring.psa;

public class FooIntroductionService implements Introduction {

  @Override
  public String doIntroduction() {
    return "Hello, I'm Foo";
  }
}
