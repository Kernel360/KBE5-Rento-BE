package com.kbe5.domain.event.service;

import com.kbe5.domain.event.entity.Event;
import java.util.List;

public interface EventService {
    void processEvent(Event event);
}
