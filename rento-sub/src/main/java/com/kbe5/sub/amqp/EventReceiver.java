package com.kbe5.sub.amqp;


import com.kbe5.domain.event.entity.Event;
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
    public void receive(Event event) throws IOException {
        log.info("Received : {}", event.getClass().getName());
        eventService.processEvent(event);
    }

}
