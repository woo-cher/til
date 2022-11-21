package com.study.til.event;

import com.study.til.spring.event.publisher.EventPublishService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("local")
class SpringEventTests {

  @Autowired
  private EventPublishService eventPublishService;

  @Test
  void springEvent() {
    eventPublishService.doBarProcess();
    eventPublishService.doFooProcess();
  }
}
