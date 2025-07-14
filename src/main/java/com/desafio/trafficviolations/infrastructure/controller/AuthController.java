package com.desafio.trafficviolations.infrastructure.controller;

import com.desafio.trafficviolations.infrastructure.security.JwtUtil;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> loginJson(@RequestBody AuthRequest request) {
        return authenticate(request.username(), request.password());
    }
/*
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<AuthResponse> loginForm(
            @RequestParam @NotBlank String username,
            @RequestParam @NotBlank String password
    ) {
        return authenticate(username, password);
    }*/

    private ResponseEntity<AuthResponse> authenticate(String username, String password) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        String token = jwtUtil.generateToken(auth.getName());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    public record AuthRequest(String username, String password) {}
    public record AuthResponse(String token) {}
}
