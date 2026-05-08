// src/main/java/org/example/puntosbonus/service/AuthService.java
package org.example.puntosbonus.service;

import lombok.RequiredArgsConstructor;
import org.example.puntosbonus.dto.LoginDTO;
import org.example.puntosbonus.model.User;
import org.example.puntosbonus.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String login(LoginDTO loginDTO) {
        // 1. Validar si el email existe ("unknown email")
        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Unknown email"));

        // 2. Validar que la contraseña coincida ("wrong password")
        // passwordEncoder.matches() compara el texto plano con el hash de la BD
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }

        // 3. Si todo está correcto, generamos y devolvemos el Token JWT
        return jwtService.generateToken(user.getEmail());
    }
}