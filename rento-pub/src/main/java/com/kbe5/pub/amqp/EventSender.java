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
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class EventSender {

    private final RabbitTemplate template;

    private final Queue queue;

    @Autowired
    public EventSender(RabbitTemplate template, @Qualifier("cycleInfo") Queue queue, StreamSender streamSender) {
        this.template = template;
        this.queue = queue;
    }

    public void send(EventCommand.CycleEventCommand command,Long mdn, DeviceToken deviceToken) {
        List<CycleData> cycleData = command.toCycleInfoEntities(deviceToken);
        CycleEvent event = command.of(deviceToken, mdn, cycleData);

        event.validateMdnMatch(mdn);

        log.info("sender : {}",event.getClass().getName());
        template.convertAndSend(queue.getName(), event);
    }

    public void send(EventCommand.OnEventCommand command, Long mdn, DeviceToken deviceToken) {
        OnOffEvent event = command.toEntity(deviceToken);
        event.validateMdnMatch(mdn);

        log.info("sender : {}",event.getClass().getName());
        template.convertAndSend(queue.getName(), event);
    }

    public void send(EventCommand.OffEventCommand command, Long mdn, DeviceToken deviceToken) {
        OnOffEvent event = command.toEntity(deviceToken);
        event.validateMdnMatch(mdn);

        log.info("sender : {}",event.getClass().getName());
        template.convertAndSend(queue.getName(), event);
    }


    public void send(GeofenceEventCommand command, Long mdn, DeviceToken deviceToken) {
        GeofenceEvent event = command.toEntity(deviceToken);
        event.validateMdnMatch(mdn);

        log.info("sender : {}",event.getClass().getName());
        template.convertAndSend(queue.getName(), event);
    }
}
