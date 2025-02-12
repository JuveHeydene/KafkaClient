package com.example.producerService.secure;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin
@RestController
@RequestMapping("/api/public")
public class AuthController {

        private AuthenticationManager authenticationManager;
        private JwtService jwtService;
        private UserService userService;

        public AuthController(AuthenticationManager authenticationManager, JwtService jwtService,
                        UserService userService) {
                this.authenticationManager = authenticationManager;
                this.jwtService = jwtService;
                this.userService = userService;
        }
        /*
         * private UserRepository userRepository;
         * private BCryptPasswordEncoder passwordEncoder;
         * 
         */

        @PostMapping("/login")
        public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
                System.out.println("loginRequest: " + loginRequest.getUsername() + " " + loginRequest.getPassword());
                Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                                                loginRequest.getPassword()));
                System.out.println("authentication: " + authentication.getName());
                String username = authentication.getName();
                List<String> roles = authentication.getAuthorities().stream()
                                .map(auth -> auth.getAuthority())
                                .collect(Collectors.toList());

                String token = jwtService.createToken(username, roles);

                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("roles", roles);
                response.put("username", username);

                return ResponseEntity.ok(response);

        }

        /*
         * User user = userRepository.findByUsername(loginRequest.getUsername())
         * .orElseThrow(() -> new UsernameNotFoundException("User not found"));
         * 
         * if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
         * {
         * throw new BadCredentialsException("Invalid password");
         * }
         * 
         * List <String> roles = new ArrayList<String>();
         * roles.add(user.getRole().toString());
         * // Génération du token JWT
         * return jwtService.createToken(user.getUsername(), roles);
         */
        @CrossOrigin
        @PostMapping("/register")
        public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {
                boolean success = userService.registerUser(request);
                if (!success) {
                        return ResponseEntity.badRequest().body("Username already taken");
                }
                return ResponseEntity.ok("User registered successfully!");
        }
}