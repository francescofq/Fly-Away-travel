// src/main/java/org/example/puntosbonus/repository/FlightRepository.java
package org.example.puntosbonus.repository;

import org.example.puntosbonus.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FlightRepository extends JpaRepository<Flight, UUID> {
    Optional<Flight> findByFlightNumber(String flightNumber);

    // Búsqueda robusta ignorando mayúsculas y evaluando rangos de fechas
    @Query("SELECT f FROM Flight f WHERE " +
            "LOWER(f.airlineName) LIKE LOWER(CONCAT('%', :airlineName, '%')) AND " +
            "LOWER(f.flightNumber) LIKE LOWER(CONCAT('%', :flightNumber, '%')) AND " +
            "f.estDepartureTime >= :fromDate AND " +
            "f.estDepartureTime <= :toDate")
    List<Flight> searchFlights(@Param("flightNumber") String flightNumber,
                               @Param("airlineName") String airlineName,
                               @Param("fromDate") LocalDateTime fromDate,
                               @Param("toDate") LocalDateTime toDate);
}