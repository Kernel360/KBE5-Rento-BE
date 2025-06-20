package com.kbe5.adapter.service;


import com.kbe5.domain.event.entity.Event;
import com.kbe5.domain.event.enums.EventType;
import com.kbe5.domain.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OnEventHandler implements EventHandler{

    private final EventRepository eventRepository;

    @Override
    public EventType getEventType() {
        return EventType.ON;
    }

    @Override
    public void handle(Event event) {

        //todo: 연동규격서 요구사항에 맞춰서 구현필요

        eventRepository.save(event);
    }
}
