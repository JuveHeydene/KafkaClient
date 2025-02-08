package com.example.producerService.controller;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Collections;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/kafka")
@Tag(name = "Kafka API", description = "Endpoints pour la gestion des topics Kafka")
public class KafkaTopicController {

    private final KafkaAdmin kafkaAdmin;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public KafkaTopicController(KafkaAdmin kafkaAdmin) {
        this.kafkaAdmin = kafkaAdmin;
    }


    @PostMapping("/createTopic")
    @Operation(summary = "Créer un topic Kafka", description = "Permet de créer un topic en envoyant son nom en paramètre.")
    public String createTopic(@RequestParam String topicName) {
        try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
            NewTopic newTopic = new NewTopic(topicName, 1, (short) 1);
            adminClient.createTopics(Collections.singletonList(newTopic)).all().get();
            return "Topic '" + topicName + "' créé avec succès.";
        } catch (ExecutionException | InterruptedException e) {
            return "Erreur lors de la création du topic : " + e.getMessage();
        }
    }
}