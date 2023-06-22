package com.study.til.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

//@Component
@Slf4j
public class KafkaListeners {

//  @KafkaListener(topics = "toytopic", groupId = "groupId")
  public void consume(final String message) {
    log.info("kafka : {}", message);
  }
}
