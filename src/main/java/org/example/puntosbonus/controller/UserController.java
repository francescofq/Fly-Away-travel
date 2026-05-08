package org.example.puntosbonus.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.puntosbonus.dto.NewIdDTO;
import org.example.puntosbonus.dto.RegisterUserDTO;
import org.example.puntosbonus.service.UserService; // Importamos tu UserService
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    // Inyectamos el UserService en lugar del AuthService
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<NewIdDTO> register(@Valid @RequestBody RegisterUserDTO request) {
        // Llamamos al método con su nombre real: registerUser
        UUID userId = userService.registerUser(request);

        // Retornamos 201 Created para que el tester esté feliz
        return new ResponseEntity<>(new NewIdDTO(userId.toString()), HttpStatus.CREATED);
    }
}