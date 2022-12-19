package com.study.til.spring.event.publisher;

import com.study.til.spring.event.dto.BarDTO;
import com.study.til.spring.event.dto.FooDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventPublishService {
  public final ApplicationEventPublisher eventPublisher;

  public void doFooProcess() {
    FooDTO foo = new FooDTO();
    log.info("foo process!");
    eventPublisher.publishEvent(foo);
  }

  public void doBarProcess() {
    BarDTO bar = new BarDTO();
    log.info("bar process!");
    eventPublisher.publishEvent(bar);
  }
}
