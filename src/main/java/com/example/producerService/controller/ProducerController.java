package com.example.producerService.controller;

import com.example.producerService.services.KafkaProducerService;
import com.example.producerService.services.KafkaProducerServiceJson;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/producer")
public class ProducerController {
    private final KafkaProducerService kafkaProducerService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ProducerController(KafkaProducerService kafkaProducerService, ObjectMapper objectMapper) {
        this.kafkaProducerService = kafkaProducerService;
    }
    
    @PostMapping("/sendjson")
    public ResponseEntity<String> sendMessage(@RequestParam String topic, @RequestBody Map<String, Object> request) {
        try {
            String messageJson = objectMapper.writeValueAsString(request);
            kafkaProducerService.sendMessage(topic, messageJson);
            return ResponseEntity.ok("Message envoyé au topic : " + topic);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur lors de l'envoi du message : " + e.getMessage());
        }
    }
}
