// src/main/java/org/example/puntosbonus/dto/NewFlightManyRequestDTO.java
package org.example.puntosbonus.dto;

import lombok.Data;
import java.util.List;

@Data
public class NewFlightManyRequestDTO {
    private List<NewFlightRequestDTO> flights;
}