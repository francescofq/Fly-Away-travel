package org.example.puntosbonus.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewFlightRequestDTO {

    @NotBlank(message = "Airline name is required")
    private String airlineName;

    @NotBlank(message = "Flight number is required")
    @Pattern(regexp = "^[A-Z]{2,3}[0-9]{3}$", message = "Invalid flight number format")
    private String flightNumber;

    @NotNull(message = "Departure time is required")
    private LocalDateTime estDepartureTime;

    @NotNull(message = "Arrival time is required")
    private LocalDateTime estArrivalTime;

    @NotNull(message = "Available seats are required")
    @Min(value = 1, message = "Must have at least 1 seat")
    private Integer availableSeats;
}