package com.example.producerService.secure;


import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

     public boolean registerUser(RegistrationRequest request) {
        System.out.println(userRepository.findByUsername(request.getUsername()));
        if (userRepository.existsByUsername(request.getUsername())) {
            return false; // Username déjà utilisé
        }

        User user = new User();
        user.setUserId(UUID.randomUUID()); // Générer un UUID unique
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Hachage du mot de passe
        String role = (user.getRole() != null) ? request.getRole() : "USER";
        user.setRole(EnumConverter.convertStringToRole(role));
        user.setEnabled(true);

        userRepository.save(user);
        return true;
    }
}