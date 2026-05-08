// src/main/java/org/example/puntosbonus/model/Flight.java
package org.example.puntosbonus.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "flights")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String airlineName;

    @Column(nullable = false, unique = true, length = 6)
    private String flightNumber;

    @Column(nullable = false)
    private LocalDateTime estDepartureTime;

    @Column(nullable = false)
    private LocalDateTime estArrivalTime;

    @Column(nullable = false)
    private Integer availableSeats;
}