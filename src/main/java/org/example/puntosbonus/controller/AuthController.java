// src/main/java/org/example/puntosbonus/controller/AuthController.java
package org.example.puntosbonus.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.puntosbonus.dto.AuthToken;
import org.example.puntosbonus.dto.LoginDTO;
import org.example.puntosbonus.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        // Tu código de login se mantiene igual...
        String token = authService.login(loginDTO);
        return ResponseEntity.ok(java.util.Map.of("token", token)); // Asegúrate de retornar la estructura {"token": "..."}
    }
}