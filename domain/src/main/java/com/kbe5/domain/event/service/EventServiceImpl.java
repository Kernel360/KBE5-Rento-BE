package com.kbe5.domain.event.service;

import com.kbe5.domain.event.entity.Event;
import com.kbe5.domain.event.enums.EventType;
import com.kbe5.domain.event.handler.EventHandler;
import com.kbe5.domain.exception.DeviceException;
import com.kbe5.domain.exception.DeviceResultCode;
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
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{
    private final Map<EventType, EventHandler> eventHandlers;

    @Autowired
    public EventServiceImpl(List<EventHandler> handlers) {
        this.eventHandlers = handlers.stream()
            .collect(Collectors.toMap(EventHandler::getEventType, Function.identity()));
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
}
