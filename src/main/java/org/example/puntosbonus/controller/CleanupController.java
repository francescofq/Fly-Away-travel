// src/main/java/org/example/puntosbonus/controller/CleanupController.java
package org.example.puntosbonus.controller;

import lombok.RequiredArgsConstructor;
import org.example.puntosbonus.repository.BookingRepository;
import org.example.puntosbonus.repository.FlightRepository;
import org.example.puntosbonus.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cleanup")
@RequiredArgsConstructor // Lombok: Crea el constructor para los atributos 'final'
public class CleanupController {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;

    @DeleteMapping
    public ResponseEntity<Void> cleanup() {
        // El orden es SUPER importante. 
        // Primero borramos la tabla que tiene llaves foráneas (bookings)
        // para evitar errores de violación de restricciones (constraints) en PostgreSQL.
        bookingRepository.deleteAll();
        flightRepository.deleteAll();
        userRepository.deleteAll();

        return ResponseEntity.ok().build();
    }
}