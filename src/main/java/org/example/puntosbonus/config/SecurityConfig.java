package org.example.puntosbonus.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // EL SALVAVIDAS: Permite que Spring envíe los verdaderos errores 400 y 500
                        .requestMatchers("/error").permitAll()

                        // Quitamos el HttpMethod para asegurarnos de que atrape todo en estas rutas
                        .requestMatchers("/users/register", "/auth/login").permitAll()
                        .requestMatchers("/flights/create", "/flights/create-many", "/flights/search").permitAll()

                        // Endpoints GET utilitarios
                        .requestMatchers(HttpMethod.GET, "/flights/**", "/users/**").permitAll()

                        // Rutas protegidas
                        .requestMatchers("/flights/book").authenticated()

                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}