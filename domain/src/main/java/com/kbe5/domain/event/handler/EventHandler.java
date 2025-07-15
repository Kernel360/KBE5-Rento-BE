package com.kbe5.domain.event.handler;


import com.kbe5.domain.device.entity.DeviceToken;
import com.kbe5.domain.event.dto.EventCommand;
import com.kbe5.domain.event.enums.EventType;

public interface EventHandler {

    EventType getEventType();

    void handle(EventCommand.Event command, DeviceToken deviceToken);
}
