package com.kbe5.pub.amqp;

import com.kbe5.domain.device.entity.DeviceToken;
import com.kbe5.domain.event.dto.EventCommand;
import com.kbe5.domain.event.entity.CycleData;
import com.kbe5.domain.event.entity.CycleEvent;

import com.kbe5.domain.event.dto.EventCommand.GeofenceEventCommand;
import com.kbe5.domain.event.dto.EventCommand.OffEventCommand;
import com.kbe5.domain.event.dto.EventCommand.OnEventCommand;
import com.kbe5.domain.event.entity.CycleEvent;
import com.kbe5.domain.event.entity.Event;
import com.kbe5.domain.event.entity.GeofenceEvent;
import com.kbe5.domain.event.entity.OnOffEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventSender {

    private final RabbitTemplate template;
    private final List<String> queueNames = List.of("cycle-info-1", "cycle-info-2", "cycle-info-3");
    private final AtomicInteger roundRobinIndex = new AtomicInteger(0);

    @Async
    public void send(EventCommand.CycleEventCommand command, Long mdn, DeviceToken deviceToken) {

        int index = roundRobinIndex.getAndUpdate(i -> (i + 1) % queueNames.size());
        String queueName = queueNames.get(index);
//        log.info("Sending cycle event to queue: {}", queueName);
//        log.info("sender : {}",command.getClass().getName());
        List<CycleData> cycleInfoEntities = command.toCycleInfoEntities(deviceToken);
        CycleEvent cycleEvent = command.of(deviceToken, mdn, cycleInfoEntities);
        template.convertAndSend(queueName, cycleEvent);
    }

    public void send(EventCommand.OnEventCommand command, Long mdn, DeviceToken deviceToken) {
        log.info("sender : {}", command.getClass().getName());
        OnOffEvent event = command.toEntity(deviceToken);
        template.convertAndSend("cycle-info-1", event);
    }

    public void send(EventCommand.OffEventCommand command, Long mdn, DeviceToken deviceToken) {
        log.info("sender : {}",command.getClass().getName());
        OnOffEvent event = command.toEntity(deviceToken);
        template.convertAndSend("cycle-info-1", event);
    }


    public void send(GeofenceEventCommand command, Long mdn, DeviceToken deviceToken) {
        log.info("sender : {}",command.getClass().getName());
        GeofenceEvent event = command.toEntity(deviceToken);
        template.convertAndSend("cycle-info-1", event);
    }
}

