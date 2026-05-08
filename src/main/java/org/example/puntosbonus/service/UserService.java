// src/main/java/org/example/puntosbonus/service/UserService.java
package org.example.puntosbonus.service;

import lombok.RequiredArgsConstructor;
import org.example.puntosbonus.dto.RegisterUserDTO;
import org.example.puntosbonus.model.User;
import org.example.puntosbonus.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UUID registerUser(RegisterUserDTO dto) {
        // Validar si el email ya existe
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already registered");
        }

        // Crear la entidad usando el Builder de Lombok
        User newUser = User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword())) // Encriptar password!
                .build();

        return userRepository.save(newUser).getId();
    }
}