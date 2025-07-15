package com.kbe5.domain.event.service;

import com.kbe5.domain.event.dto.EventCommand;
import com.kbe5.domain.event.dto.EventCommand.Event;
import java.util.List;

public interface EventService {

    void processCommand(EventCommand.Event command);
}
