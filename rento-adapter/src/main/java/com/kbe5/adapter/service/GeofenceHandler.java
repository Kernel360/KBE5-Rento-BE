package com.kbe5.adapter.service;


import com.kbe5.domain.event.entity.Event;
import com.kbe5.domain.event.enums.EventType;
import com.kbe5.domain.event.repository.EventRepository;
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
