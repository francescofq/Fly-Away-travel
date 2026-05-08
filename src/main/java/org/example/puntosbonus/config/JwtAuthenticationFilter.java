// src/main/java/org/example/puntosbonus/config/JwtAuthenticationFilter.java
package org.example.puntosbonus.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.puntosbonus.model.User;
import org.example.puntosbonus.repository.UserRepository;
import org.example.puntosbonus.service.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // Si no hay token o no empieza con "Bearer ", ignoramos y dejamos que Spring Security lo bloquee (si la ruta es protegida)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7); // Quitamos la palabra "Bearer "

        try {
            final String userEmail = jwtService.extractUsername(jwt);

            // Si hay email y el usuario no está ya autenticado en este hilo
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                if (jwtService.isTokenValid(jwt)) {
                    // Buscamos al usuario real en la base de datos
                    User user = userRepository.findByEmail(userEmail).orElse(null);

                    if (user != null) {
                        // Lo inyectamos en el contexto de seguridad de Spring
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                user, null, new ArrayList<>());
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }
        } catch (Exception e) {
            // Si falla el token (expirado, malformado), simplemente no seteamos la autenticación.
        }

        filterChain.doFilter(request, response);
    }
}