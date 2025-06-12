package com.kbe5.rento.domain.event.service;

import com.kbe5.rento.domain.event.entity.Event;
import com.kbe5.rento.domain.event.enums.EventType;
import com.kbe5.rento.domain.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GeofenceHandler implements EventHandler {

    private final EventRepository eventRepository;

    @Override
    public EventType getEventType() {

        return EventType.GEOFENCE;
    }

    @Override
    public void handle(Event event) {

        eventRepository.save(event);
    }
}
