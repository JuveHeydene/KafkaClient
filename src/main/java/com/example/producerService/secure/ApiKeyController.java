package com.example.producerService.secure;

import org.springframework.web.bind.annotation.*;
import java.security.SecureRandom;
import java.util.Base64;

@RestController
@RequestMapping("/api/key")
public class ApiKeyController {

    private static final int TOKEN_SIZE = 32; // Taille en octets (256 bits)

    @PostMapping("/generate")
    public ApiKeyResponse generateApiKey() {
        // Générer un token aléatoire
        String apiKey = generateRandomKey(TOKEN_SIZE);
        

        return new ApiKeyResponse(apiKey);
    }

    private String generateRandomKey(int size) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[size];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    static class ApiKeyResponse {
        private String apiKey;
    

        public ApiKeyResponse(String apiKey) {
            this.apiKey = apiKey;
        }
           

        public String getApiKey() {
            return apiKey;
        }

       
    }
}