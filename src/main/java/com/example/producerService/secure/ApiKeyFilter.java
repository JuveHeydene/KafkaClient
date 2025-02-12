package com.example.producerService.secure;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.lang.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")  // Load the secret key from application.properties
    private String secretKey;

    private final ApiKeyRepository apiKeyRepository; // Inject the repository to check API keys

    public ApiKeyFilter(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // Check for API key in the headers
        String apiKey = request.getHeader("x-api-key");
        if (apiKey != null && !apiKey.isEmpty()) {
            if (!apiKeyRepository.existsByKey(apiKey)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Clé API invalide");
                return;
            }
        }

        // Check for JWT in the Authorization header
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace("Bearer ", "");

        try {
            // Validate and extract the JWT
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();

            // Check if the token contains roles and extract them
            List<?> rolesRaw = Optional.ofNullable(claims.get("roles", List.class)).orElse(List.of());
            List<String> roles = rolesRaw.stream()
                    .filter(role -> role instanceof String)
                    .map(role -> (String) role)
                    .collect(Collectors.toList());

            // Create the authentication for Spring Security
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la validation du token: " + e.getMessage());
            // On clear le contexte et renvoie une erreur 401 en cas d'échec
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token invalide ou expiré");
            return;
        }

        filterChain.doFilter(request, response);
    }
}