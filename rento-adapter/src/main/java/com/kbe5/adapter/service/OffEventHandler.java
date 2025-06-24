package com.kbe5.adapter.service;


import com.kbe5.domain.event.entity.Event;
import com.kbe5.domain.event.enums.EventType;
import com.kbe5.domain.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OffEventHandler implements EventHandler {

    private final EventRepository eventRepository;
    private final CycleInfoSummaryService cycleInfoSummaryService;

    @Override
    public EventType getEventType() {
        return EventType.OFF;
    }

    @Override
    public void handle(Event event) {

        //todo: 연동규격서 요구사항에 맞춰서 구현필요

        eventRepository.save(event);
        cycleInfoSummaryService.create(event.getDriveId());
    }
}
