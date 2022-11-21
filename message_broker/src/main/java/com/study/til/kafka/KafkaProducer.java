package com.study.til.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {
  private final KafkaTemplate<String, String> template;

  public void produce(String message) {
    template.send("toytopic", message);
  }
}
