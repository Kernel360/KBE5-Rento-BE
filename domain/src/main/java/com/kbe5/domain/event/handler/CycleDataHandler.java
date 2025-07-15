package com.kbe5.domain.event.handler;


import com.kbe5.domain.device.entity.DeviceToken;
import com.kbe5.domain.event.dto.EventCommand;
import com.kbe5.domain.event.dto.EventCommand.CycleEventCommand;
import com.kbe5.domain.event.dto.EventCommand.CycleInfoCommand;
import com.kbe5.domain.event.entity.CycleData;
import com.kbe5.domain.event.entity.CycleEvent;
import com.kbe5.domain.event.entity.Event;
import com.kbe5.domain.event.enums.EventType;
import com.kbe5.domain.event.service.EventStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class CycleDataHandler implements EventHandler {

    private final EventStore eventStore;

    @Override
    public EventType getEventType() {
        return EventType.CYCLE_DATA;
    }

    @Override
    public void handle(EventCommand.Event command, DeviceToken deviceToken) {
        CycleEventCommand cycleEventCommand = (CycleEventCommand) command;

        List<CycleData> cycleData = cycleEventCommand.toCycleInfoEntities(deviceToken);
        CycleEvent event = cycleEventCommand.of(deviceToken, deviceToken.getMdn(), cycleData);

        log.info("CycleDataHandler : {}", event.getClass().getName());
        log.info("CycleDataCount : {}", cycleData.size());
        eventStore.store(event);

        if (isNotNullAndNotEmpty(cycleData)){
            eventStore.bulkInsert(cycleData);
        }
    }

    private boolean isNotNullAndNotEmpty(List<CycleData> cycleData) {
        return cycleData != null && !cycleData.isEmpty();
    }
}
