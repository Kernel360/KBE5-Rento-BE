package com.kbe5.domain.event.handler;


import com.kbe5.domain.device.entity.DeviceToken;
import com.kbe5.domain.event.dto.EventCommand;
import com.kbe5.domain.event.dto.EventCommand.OnEventCommand;
import com.kbe5.domain.event.entity.OnOffEvent;
import com.kbe5.domain.event.enums.EventType;
import com.kbe5.domain.event.service.EventStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OnEventHandler implements EventHandler {

    private final EventStore eventStore;
    @Override
    public EventType getEventType() {
        return EventType.ON;
    }

    @Override
    public void handle(EventCommand.Event command, DeviceToken deviceToken) {

        //todo: 연동규격서 요구사항에 맞춰서 구현필요
        OnEventCommand onEventCommand = (OnEventCommand) command;
        OnOffEvent onEvent = onEventCommand.toEntity(deviceToken);
        eventStore.store(onEvent);
    }
}
