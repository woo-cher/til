package com.study.til;

import com.study.til.event.listener.SpringEventListener;
import com.study.til.event.publisher.EventPublishService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {
    EventPublishService.class, SpringEventListener.class
})
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
