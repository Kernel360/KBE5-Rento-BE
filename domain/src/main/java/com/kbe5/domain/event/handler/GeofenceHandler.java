package com.kbe5.domain.event.handler;


import com.kbe5.domain.device.entity.DeviceToken;
import com.kbe5.domain.event.dto.EventCommand;
import com.kbe5.domain.event.dto.EventCommand.GeofenceEventCommand;
import com.kbe5.domain.event.entity.GeofenceEvent;
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
    public void handle(EventCommand.Event command, DeviceToken deviceToken) {
        GeofenceEventCommand geofenceEventCommand = (GeofenceEventCommand) command;
        GeofenceEvent geofenceEvent = geofenceEventCommand.toEntity(deviceToken);
        eventStore.store(geofenceEvent);
    }
}
