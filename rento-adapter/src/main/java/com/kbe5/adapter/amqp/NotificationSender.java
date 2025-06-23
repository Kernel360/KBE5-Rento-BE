package com.kbe5.adapter.amqp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class NotificationSender {
    private final RabbitTemplate rabbitTemplate;

    private final Queue queue;

    @Autowired
    public NotificationSender(RabbitTemplate rabbitTemplate, @Qualifier("notification")Queue queue) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
    }

    public void send(Long driveId){
        log.info("Sending notification to Queue driveID: {}", driveId);

        rabbitTemplate.convertAndSend(queue.getName(), driveId);
    }
}
