package com.example.producerService.services;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


public class KafkaProducerServiceJson {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerServiceJson(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}
