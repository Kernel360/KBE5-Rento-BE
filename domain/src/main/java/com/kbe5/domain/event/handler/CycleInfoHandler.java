package com.kbe5.domain.event.handler;


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
public class CycleInfoHandler implements EventHandler {

    private final EventStore eventStore;

    @Override
    public EventType getEventType() {
        return EventType.CYCLE_INFO;
    }

    @Override
    public void handle(Event event) {

        CycleEvent cycleEvent = (CycleEvent) event;
        List<CycleData> cycleData = cycleEvent.getCycleData();

        eventStore.store(cycleEvent);

        if (isNotNullAndNotEmpty(cycleData)){
            eventStore.bulkInsert(cycleData);
        }
    }

    private boolean isNotNullAndNotEmpty(List<CycleData> cycleData) {
        return cycleData != null && !cycleData.isEmpty();
    }
}
