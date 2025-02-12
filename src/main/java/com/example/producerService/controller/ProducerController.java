package com.example.producerService.controller;

import com.example.producerService.services.KafkaProducerService;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/producer")
public class ProducerController {
    private final KafkaProducerService kafkaProducerService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ProducerController(KafkaProducerService kafkaProducerService, ObjectMapper objectMapper) {
        this.kafkaProducerService = kafkaProducerService;
    }
    
    @PostMapping("/push-content")
    @Operation(summary = "Endpoint de publication d'un message dans un topic de communication", description = "Endpoint qui sera utilisé par chaque service afin de publier des données à partir des topics de communication.")
    public ResponseEntity<String> sendMessage(@RequestParam String topic, @RequestBody Object request) {
        try {
            // Sérialisation de l'objet DTO en JSON
            String messageJson = objectMapper.writeValueAsString(request);
            
            // Envoi du message au topic Kafka
            kafkaProducerService.sendMessage(topic, messageJson);

            return ResponseEntity.ok(" Message envoyé au topic : " + topic);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(" Erreur lors de l'envoi du message : " + e.getMessage());
        }
    }
}
