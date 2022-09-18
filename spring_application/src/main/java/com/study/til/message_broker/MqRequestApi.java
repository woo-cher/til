package com.study.til.message_broker;

import com.study.til.message_broker.kafka.KafkaProducer;
import com.study.til.message_broker.rabbitmq.RabbitmqPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class MqRequestApi {
    private final KafkaProducer producer;
    private final RabbitmqPublisher sender;

    @PostMapping("/kafka-messages")
    public String sendKafkaMessage(@RequestBody Map<String, String> request) {
        String message = request.get("message");

        try {
            producer.produce(message);
            return "kafka : ok";
        } catch (Exception e) {
            return "kafka : fail";
        }
    }

    @PostMapping("/rabbitmq-messages")
    public String sendRabbitmqMessage(final @RequestBody Map<String, String> request) {
        String message = request.get("message");

        try {
            this.sender.publish(message);
            return "ok";
        } catch (Exception e) {
            return "fail";
        }
    }

}
