// src/main/java/org/example/puntosbonus/repository/BookingRepository.java
package org.example.puntosbonus.repository;

import org.example.puntosbonus.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
}