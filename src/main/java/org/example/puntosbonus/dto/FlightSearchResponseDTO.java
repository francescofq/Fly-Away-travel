// src/main/java/org/example/puntosbonus/dto/FlightSearchResponseDTO.java
package org.example.puntosbonus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class FlightSearchResponseDTO {
    // Envolvemos la lista para mantener la escalabilidad de la respuesta
    private List<FlightResponseDTO> flights;
}