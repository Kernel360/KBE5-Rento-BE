package com.kbe5.sub.amqp;


import com.kbe5.domain.event.dto.EventCommand;
import com.kbe5.domain.event.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
@RabbitListener(queues = "cycle-info")
public class EventReceiver {

    private final EventService eventService;

    @RabbitHandler
    public void receive(EventCommand.Event command) throws IOException {
        log.info("Received : {}", command.getClass().getName());
        log.info("ReceivedToken : {}", command.getToken());
        log.info("ReceviedOtime : {}", command.getOTime());
        eventService.processCommand(command);
    }
}
