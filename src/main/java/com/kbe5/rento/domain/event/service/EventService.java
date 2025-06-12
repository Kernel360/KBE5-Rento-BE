package com.kbe5.rento.domain.event.service;

import com.kbe5.rento.common.exception.DeviceException;
import com.kbe5.rento.domain.device.enums.DeviceResultCode;
import com.kbe5.rento.domain.event.entity.Event;
import com.kbe5.rento.domain.event.enums.EventType;
import com.kbe5.rento.domain.event.repository.EventRepository;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class EventService {

    private final Map<EventType, EventHandler> eventHandlers;

    private final EventRepository eventRepository;

    @Autowired
    public EventService(List<EventHandler> handlers, EventRepository eventRepository) {
        this.eventHandlers = handlers.stream()
            .collect(Collectors.toMap(EventHandler::getEventType, Function.identity()));
        this.eventRepository = eventRepository;
    }

    @Transactional
    public void processEvent(Event event) {

        log.info("processEvent receive {}", event.getClass().getName());

        EventHandler handler = eventHandlers.get(event.getEventType());
        if (handler != null) {
            handler.handle(event);
        }else {
            throw new DeviceException(DeviceResultCode.UNDEFINED_ERROR);
        }
    }

    public List<Event> getList() {

        return eventRepository.findAll();
    }
}
