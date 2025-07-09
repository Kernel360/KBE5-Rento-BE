package com.kbe5.domain.event.handler;


import com.kbe5.domain.event.entity.Event;
import com.kbe5.domain.event.enums.EventType;
import com.kbe5.domain.event.service.EventStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GeofenceHandler implements EventHandler {

    private final EventStore eventStore;

    @Override
    public EventType getEventType() {

        return EventType.GEOFENCE;
    }

    @Override
    public void handle(Event event) {

        eventStore.store(event);
    }
}
