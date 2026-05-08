package org.example.puntosbonus.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.puntosbonus.dto.*;
import org.example.puntosbonus.model.Flight;
import org.example.puntosbonus.model.User;
import org.example.puntosbonus.service.BookingService;
import org.example.puntosbonus.service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;
    private final BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<NewIdDTO> create(@Valid @RequestBody NewFlightRequestDTO newFlight) {
        UUID newId = flightService.createFlight(newFlight);
        // Aquí está el cambio clave: HttpStatus.CREATED (201)
        return new ResponseEntity<>(new NewIdDTO(newId.toString()), HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<FlightSearchResponseDTO> search(
            @RequestParam(required = false) String flightNumber,
            @RequestParam(required = false) String airlineName,
            @RequestParam(required = false) String estDepartureTimeFrom,
            @RequestParam(required = false) String estDepartureTimeTo) {
        return ResponseEntity.ok(flightService.searchFlights(flightNumber, airlineName, estDepartureTimeFrom, estDepartureTimeTo));
    }

    @PostMapping("/create-many")
    public ResponseEntity<?> createMany(@RequestBody NewFlightManyRequestDTO requestDTO) {
        flightService.createManyFlights(requestDTO.getFlights());
        // Dejamos un 200 OK genérico para no complicarnos con DTOs que quizás no tengas
        return ResponseEntity.ok().body("Processing flights asynchronously");
    }

    @PostMapping("/book")
    public ResponseEntity<NewIdDTO> book(@RequestBody FlightBookRequestDTO requestDTO,
                                         @AuthenticationPrincipal User user) {
        UUID bookingId = bookingService.bookFlight(requestDTO.getFlightId(), user);
        // También 201 Created para las reservas
        return new ResponseEntity<>(new NewIdDTO(bookingId.toString()), HttpStatus.OK);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookingInfoDTO> getBooking(@PathVariable UUID id) {
        return ResponseEntity.ok(bookingService.getBookingInfo(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlight(@PathVariable UUID id) {
        return ResponseEntity.ok(flightService.getFlightById(id));
    }
}