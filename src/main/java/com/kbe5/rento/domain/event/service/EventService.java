package com.kbe5.rento.domain.event.service;

import com.kbe5.rento.common.exception.DeviceException;
import com.kbe5.rento.domain.device.entity.DeviceToken;
import com.kbe5.rento.domain.device.enums.DeviceResultCode;
import com.kbe5.rento.domain.event.amqp.EventSender;
import com.kbe5.rento.domain.event.entity.CycleEvent;
import com.kbe5.rento.domain.event.entity.Event;
import com.kbe5.rento.domain.event.entity.OnOffEvent;
import com.kbe5.rento.domain.event.enums.EventType;
import com.kbe5.rento.domain.event.repository.EventRepository;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class EventService {

    private final EventRepository eventRepository;

    private final Map<EventType, EventHandler> eventHandlers;

    @Autowired
    public EventService(EventRepository eventRepository, List<EventHandler> handlers) {
        this.eventRepository = eventRepository;
        this.eventHandlers = handlers.stream()
            .collect(Collectors.toMap(EventHandler::getEventType, Function.identity()));
    }

    @Transactional
    public CycleEvent saveCycleEvent(CycleEvent cycleEvent) {

        return eventRepository.save(cycleEvent);
    }

    @Transactional
    public void saveEvent(Event event) {

        EventHandler handler = eventHandlers.get(event.getEventType());
        if (handler != null) {
            handler.handle(event);
        }else {
            throw new DeviceException(DeviceResultCode.UNDEFINED_ERROR);
        }
    }

}
