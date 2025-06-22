package com.kbe5.adapter.amqp;

import com.kbe5.domain.event.entity.Event;
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

    @Autowired
    public EventSender(RabbitTemplate template, @Qualifier("cycleInfo") Queue queue) {
        this.template = template;
        this.queue = queue;
    }

    public void send(Event event, Long mdn) {

        event.validateMdnMatch(mdn);

        log.info("sender : {}",event.getClass().getName());
        template.convertAndSend(queue.getName(), event);
    }
}
