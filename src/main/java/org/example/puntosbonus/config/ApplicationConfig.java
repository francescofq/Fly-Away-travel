package org.example.puntosbonus.config;

import lombok.RequiredArgsConstructor;
import org.example.puntosbonus.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                java.util.Optional<org.example.puntosbonus.model.User> optionalUser = userRepository.findByEmail(username);

                if (!optionalUser.isPresent()) {
                    throw new UsernameNotFoundException("User not found");
                }

                org.example.puntosbonus.model.User miUsuario = optionalUser.get();

                return org.springframework.security.core.userdetails.User.builder()
                        .username(miUsuario.getEmail())
                        .password(miUsuario.getPassword())
                        .authorities("ROLE_USER")
                        .build();
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}