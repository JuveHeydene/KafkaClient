package com.example.producerService.controller;

import com.example.producerService.services.KafkaProducerService;
import com.example.producerService.services.KafkaProducerServiceJson;
import org.springframework.web.bind.annotation.*;


import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/producer")
public class ProducerController {
    private final KafkaProducerService kafkaProducerService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ProducerController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message) {
        kafkaProducerService.sendMessage("test-topic", message);
        return "Message envoyé : " + message;
    }


    @PostMapping("/sendjson")
    public String sendMessage(@RequestBody Map<String, Object> request) {
        try {
            String messageJson = objectMapper.writeValueAsString(request);
            kafkaProducerService.sendMessage("json-topic", messageJson);
            return "Message envoyé au topic json-topic";
        } catch (Exception e) {
            return "Erreur lors de l'envoi du message: " + e.getMessage();
        }
    }
}
