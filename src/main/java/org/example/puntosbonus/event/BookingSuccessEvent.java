// src/main/java/org/example/puntosbonus/event/BookingSuccessEvent.java
package org.example.puntosbonus.event;

import lombok.Getter;
import org.example.puntosbonus.model.Booking;
import org.springframework.context.ApplicationEvent;

@Getter
public class BookingSuccessEvent extends ApplicationEvent {
    private final Booking booking;

    public BookingSuccessEvent(Object source, Booking booking) {
        super(source);
        this.booking = booking;
    }
}