package org.example.puntosbonus.service;

import lombok.RequiredArgsConstructor;
import org.example.puntosbonus.dto.BookingInfoDTO;
import org.example.puntosbonus.event.BookingSuccessEvent;
import org.example.puntosbonus.model.Booking;
import org.example.puntosbonus.model.Flight;
import org.example.puntosbonus.model.User;
import org.example.puntosbonus.repository.BookingRepository;
import org.example.puntosbonus.repository.FlightRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public UUID bookFlight(UUID flightId, User user) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new IllegalArgumentException("Flight not found"));

        // Cambiado a IllegalArgumentException para que el tester reciba 400 y no 500
        if (flight.getAvailableSeats() <= 0) {
            throw new IllegalArgumentException("No seats available");
        }

        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightRepository.save(flight);

        Booking booking = Booking.builder()
                .bookingDate(LocalDateTime.now())
                .flight(flight)
                .user(user)
                .build();

        Booking savedBooking = bookingRepository.save(booking);
        eventPublisher.publishEvent(new BookingSuccessEvent(this, savedBooking));

        return savedBooking.getId();
    }

    public BookingInfoDTO getBookingInfo(UUID id) {
        Booking b = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        return BookingInfoDTO.builder()
                .id(b.getId())
                .bookingDate(b.getBookingDate())
                .flightId(b.getFlight().getId())
                .flightNumber(b.getFlight().getFlightNumber())
                .customerId(b.getUser().getId())
                .customerFirstName(b.getUser().getFirstName())
                .customerLastName(b.getUser().getLastName())
                // Agregamos los tiempos del vuelo al DTO de respuesta:
                .estDepartureTime(b.getFlight().getEstDepartureTime())
                .estArrivalTime(b.getFlight().getEstArrivalTime())
                .build();
    }
}