package com.study.til.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitmqListener {

    @RabbitListener(queues = "toy.queue")
    public void receiveMessage(final Message message) {
        log.info("amqp message : {}", message.toString());
    }
}
