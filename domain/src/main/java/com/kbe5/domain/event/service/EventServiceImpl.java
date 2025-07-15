package com.kbe5.domain.event.service;

import com.kbe5.domain.device.entity.DeviceToken;
import com.kbe5.domain.device.service.DeviceStore;
import com.kbe5.domain.event.dto.EventCommand;
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
    private final DeviceStore deviceStore;

    @Autowired
    public EventServiceImpl(List<EventHandler> handlers, DeviceStore deviceStore) {
        this.eventHandlers = handlers.stream()
            .collect(Collectors.toMap(EventHandler::getEventType, Function.identity()));
        this.deviceStore = deviceStore;
    }

    @Transactional
    public void processCommand(EventCommand.Event command) {
        String token = command.getToken();
        DeviceToken deviceToken = deviceStore.findDeviceToken(token);
        EventHandler handler = eventHandlers.get(command.getEventType());

        log.info("processEvent receive {}", command.getClass().getName());
        log.info("processEvent Token {}", deviceToken);
        log.info("processEvent eventType {}", command.getEventType());
        log.info("processEvent handler{}", handler.getClass().getName());

        if (handler != null) {
            handler.handle(command, deviceToken);
        }else {
            throw new DeviceException(DeviceResultCode.UNDEFINED_ERROR);
        }
    }
}
