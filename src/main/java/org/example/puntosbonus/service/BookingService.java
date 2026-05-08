// src/main/java/org/example/puntosbonus/service/BookingService.java
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

        if (flight.getAvailableSeats() <= 0) {
            throw new IllegalStateException("No seats available");
        }

        // Restar un asiento
        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightRepository.save(flight);

        Booking booking = Booking.builder()
                .bookingDate(LocalDateTime.now())
                .flight(flight)
                .user(user)
                .build();

        // 1. Guardamos la reserva en una variable para no perder la referencia
        Booking savedBooking = bookingRepository.save(booking);

        // 2. Disparamos el evento usando esa variable
        // Esto es lo que genera el archivo .txt que pide el README
        eventPublisher.publishEvent(new BookingSuccessEvent(this, savedBooking));

        // 3. Devolvemos el ID de la reserva guardada
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
                .build();
    }
}