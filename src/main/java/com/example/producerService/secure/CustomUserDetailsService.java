package com.example.producerService.secure;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;




import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("🔍 Recherche utilisateur: " + username);

        try {
            return userRepository.findByUsername(username)
                    .map(user -> {
                        String role = (user.getRole() != null) ? "ROLE_" + user.getRole().name() : "ROLE_USER";
                        System.out.println("✅ Utilisateur trouvé: " + username + " | Rôle: " + role);

                        return new org.springframework.security.core.userdetails.User(
                                user.getUsername(),
                                user.getPassword(),
                                Collections.singletonList(new SimpleGrantedAuthority(role)));
                    })
                    .orElseThrow(() -> {
                        System.out.println("⚠️ Utilisateur non trouvé: " + username);
                        return new UsernameNotFoundException("Utilisateur introuvable : " + username);
                    });
        } catch (Exception e) {
            System.out.println("❌ Erreur lors de la recherche de l'utilisateur: " + e.getMessage());
            throw new UsernameNotFoundException("Erreur lors de la recherche de l'utilisateur : " + username, e);
        }
    }

}