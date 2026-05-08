package org.example.puntosbonus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightSearchResponseDTO {
    // EL NOMBRE DEBE SER "items"
    private List<FlightResponseDTO> items;
}