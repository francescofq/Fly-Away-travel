package org.example.puntosbonus.service;

import lombok.RequiredArgsConstructor;
import org.example.puntosbonus.dto.NewFlightRequestDTO;
import org.example.puntosbonus.model.Flight;
import org.example.puntosbonus.repository.FlightRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.example.puntosbonus.dto.FlightResponseDTO;
import org.example.puntosbonus.dto.FlightSearchResponseDTO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;

    public UUID createFlight(NewFlightRequestDTO dto) {
        if (!dto.getEstDepartureTime().isBefore(dto.getEstArrivalTime())) {
            throw new IllegalArgumentException("Estimated Departure Time must be strictly before Estimated Arrival Time");
        }

        if (flightRepository.findByFlightNumber(dto.getFlightNumber()).isPresent()) {
            throw new IllegalArgumentException("Flight Number already exists");
        }

        Flight newFlight = Flight.builder()
                .airlineName(dto.getAirlineName())
                .flightNumber(dto.getFlightNumber())
                .estDepartureTime(dto.getEstDepartureTime())
                .estArrivalTime(dto.getEstArrivalTime())
                .availableSeats(dto.getAvailableSeats())
                .build();

        return flightRepository.save(newFlight).getId();
    }

    public FlightSearchResponseDTO searchFlights(String flightNumber, String airlineName,
                                                 String estDepartureTimeFrom, String estDepartureTimeTo) {

        String searchFlight = (flightNumber == null) ? "" : flightNumber;
        String searchAirline = (airlineName == null) ? "" : airlineName;

        LocalDateTime fromDate = (estDepartureTimeFrom != null && !estDepartureTimeFrom.isBlank())
                ? LocalDateTime.parse(estDepartureTimeFrom)
                : LocalDateTime.of(2000, 1, 1, 0, 0);

        LocalDateTime toDate = (estDepartureTimeTo != null && !estDepartureTimeTo.isBlank())
                ? LocalDateTime.parse(estDepartureTimeTo)
                : LocalDateTime.of(2100, 12, 31, 23, 59);

        // LLAMADA CLAVE: El orden aquí coincide con el Repository
        List<Flight> flights = flightRepository.searchFlights(searchFlight, searchAirline, fromDate, toDate);

        List<FlightResponseDTO> dtos = flights.stream().map(flight -> FlightResponseDTO.builder()
                .id(flight.getId())
                .airlineName(flight.getAirlineName())
                .flightNumber(flight.getFlightNumber())
                .estDepartureTime(flight.getEstDepartureTime())
                .estArrivalTime(flight.getEstArrivalTime())
                .availableSeats(flight.getAvailableSeats())
                .build()).collect(Collectors.toList());

        return new FlightSearchResponseDTO(dtos);
    }

    @Async
    public void createManyFlights(List<NewFlightRequestDTO> dtos) {
        dtos.forEach(this::createFlight);
    }

    public Flight getFlightById(UUID id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Flight not found"));
    }
}