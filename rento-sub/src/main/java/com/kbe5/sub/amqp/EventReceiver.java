package com.kbe5.sub.amqp;


import com.kbe5.domain.event.entity.Event;
import com.kbe5.domain.event.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
@RabbitListener(queues = {"cycle-info-1", "cycle-info-2", "cycle-info-3"}, concurrency = "12")
public class EventReceiver {

    public static int count = 0;
    private final EventService eventService;

    @RabbitHandler
    public void receive(Event event, @Header(AmqpHeaders.CONSUMER_QUEUE) String queueName) throws IOException {
        log.info("Received event from queue {}: {}", queueName, event);
        log.info("receiver count : {}", count++);
        eventService.processEvent(event);
    }
}
