package com.kbe5.rento.domain.event.service;

import com.kbe5.rento.domain.event.entity.Event;
import com.kbe5.rento.domain.event.enums.EventType;

public interface EventHandler {

    EventType getEventType();

    void handle(Event event);
}
