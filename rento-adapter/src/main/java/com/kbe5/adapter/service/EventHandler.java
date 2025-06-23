package com.kbe5.adapter.service;


import com.kbe5.domain.event.entity.Event;
import com.kbe5.domain.event.enums.EventType;

public interface EventHandler {

    EventType getEventType();

    void handle(Event event);
}
