package org.example.puntosbonus.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class BookingInfoDTO {
    private UUID id;
    private LocalDateTime bookingDate;
    private UUID flightId;
    private String flightNumber;
    private UUID customerId;
    private String customerFirstName;
    private String customerLastName;
    // Estos dos son obligatorios para el tester:
    private LocalDateTime estDepartureTime;
    private LocalDateTime estArrivalTime;
}