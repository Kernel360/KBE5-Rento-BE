package com.kbe5.pub.amqp;

import com.kbe5.domain.device.entity.DeviceToken;
import com.kbe5.domain.event.dto.EventCommand;
import com.kbe5.domain.event.dto.EventCommand.CycleInfoCommand;
import com.kbe5.domain.event.dto.EventCommand.OffEventCommand;
import com.kbe5.domain.event.dto.EventCommand.OnEventCommand;
import com.kbe5.domain.event.entity.CycleEvent;
import com.kbe5.domain.event.entity.CycleInfo;
import com.kbe5.domain.event.entity.Event;
import com.kbe5.domain.event.entity.OnOffEvent;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventSender {

    private final RabbitTemplate template;

    private final Queue queue;

    private final StreamSender streamSender;

    @Autowired
    public EventSender(RabbitTemplate template, @Qualifier("cycleInfo") Queue queue, StreamSender streamSender) {
        this.template = template;
        this.queue = queue;
        this.streamSender = streamSender;
    }

    public void send(EventCommand.CycleEventCommand command,Long mdn, DeviceToken deviceToken) {
        List<CycleInfo> cycleInfos = command.toCycleInfoEntities(deviceToken);
        CycleEvent event = command.of(deviceToken, mdn, cycleInfos);

        event.validateMdnMatch(mdn);

        log.info("sender : {}",event.getClass().getName());
        template.convertAndSend(queue.getName(), event);
        cycleInfos.forEach(streamSender::send);
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
}
