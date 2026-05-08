// src/main/java/org/example/puntosbonus/dto/FlightResponseDTO.java
package org.example.puntosbonus.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class FlightResponseDTO {
    private UUID id;
    private String airlineName;
    private String flightNumber;
    private LocalDateTime estDepartureTime;
    private LocalDateTime estArrivalTime;
    private Integer availableSeats;
}