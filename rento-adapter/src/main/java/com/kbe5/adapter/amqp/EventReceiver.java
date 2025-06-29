package com.kbe5.adapter.amqp;


import com.kbe5.adapter.service.EventService;
import com.kbe5.domain.event.entity.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@RabbitListener(queues = "cycle-info")
public class EventReceiver {

    private final EventService eventService;

    //event 발생시 기본
    @RabbitHandler
    public void receive(Event event) {

        log.info("Received : {}", event.getClass().getName());
        eventService.processEvent(event);
    }

}
