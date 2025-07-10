package com.kbe5.domain.event.handler;


import com.kbe5.domain.event.entity.Event;
import com.kbe5.domain.event.enums.EventType;
import com.kbe5.domain.event.service.EventStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OffEventHandler implements EventHandler {

    private final EventStore eventStore;
//    private final CycleDataSummaryService cycleInfoSummaryService;

    @Override
    public EventType getEventType() {
        return EventType.OFF;
    }

    @Override
    public void handle(Event event) {

        //todo: 연동규격서 요구사항에 맞춰서 구현필요

        eventStore.store(event);
//        cycleInfoSummaryService.create(event.getDriveId());
    }
}
