package com.study.til.aop;

import com.study.til.spring.aop.SomeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class AopTests {

  @Autowired private SomeService someService;

  @Test
  public void beforeAndAfter() {
    someService.saveSomeObject();
  }

  @Test
  public void afterReturning() {
    someService.getSomeMessage();
  }

  @Test
  public void aspectsWithAnnotation() {
    someService.doSomething();
  }
}
