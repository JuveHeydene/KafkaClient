package com.example.producerService.secure;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegistrationRequest {
    private String username;
    private String email;
    private String password;
    private String role;

    // Getters et setters
}