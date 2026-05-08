// src/main/java/org/example/puntosbonus/dto/FlightBookRequestDTO.java
package org.example.puntosbonus.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class FlightBookRequestDTO {
    private UUID flightId;
}