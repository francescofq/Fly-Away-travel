// src/main/java/org/example/puntosbonus/event/BookingEventListener.java
package org.example.puntosbonus.event;

import org.example.puntosbonus.model.Booking;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Component
public class BookingEventListener {

    @EventListener
    public void handleBookingSuccess(BookingSuccessEvent event) {
        Booking b = event.getBooking();
        String fileName = "flight_booking_email_" + b.getId() + ".txt";

        DateTimeFormatter isoFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

        String content = String.format(
                "Hello %s %s,\n\n" +
                        "Your booking was successful! \n\n" +
                        "The booking is for flight %s with departure date of %s and arrival date of %s.\n\n" +
                        "The booking was registered at %s.\n\n" +
                        "Bon Voyage!\n" +
                        "Fly Away Travel",
                b.getUser().getFirstName(), b.getUser().getLastName(),
                b.getFlight().getFlightNumber(),
                b.getFlight().getEstDepartureTime().format(isoFormat),
                b.getFlight().getEstArrivalTime().format(isoFormat),
                b.getBookingDate().format(isoFormat)
        );

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}