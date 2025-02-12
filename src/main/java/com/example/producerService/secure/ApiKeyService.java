package com.example.producerService.secure;


import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class ApiKeyService {

    private final ApiKeyRepository apiKeyRepository; // Votre repository pour l'accès à la base de données

    public ApiKeyService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    public ApiKey createApiKey(UUID clientId) {
        UUID keyId = UUID.randomUUID();
        String key = UUID.randomUUID().toString(); // Générer une clé API
        ApiKey apiKey = new ApiKey(keyId, clientId, key, true); // Clé activée par défaut
        return apiKeyRepository.save(apiKey); // Enregistrer dans la base de données
    }
}