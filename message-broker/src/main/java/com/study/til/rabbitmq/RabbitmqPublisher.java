package com.study.til.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitmqPublisher {
  private final RabbitMessagingTemplate template;

  public void publish(String message) {
    template.convertAndSend("toy.exchange", "routeKey", message);
  }
}
