// src/main/java/org/example/puntosbonus/repository/UserRepository.java
package org.example.puntosbonus.repository;

import org.example.puntosbonus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    // Spring Data JPA arma el query SQL por nosotros usando el nombre del método
    Optional<User> findByEmail(String email);
}