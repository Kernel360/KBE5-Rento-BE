package com.kbe5.adapter.amqp;


import com.kbe5.adapter.service.EventService;
import com.kbe5.domain.event.entity.Event;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
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
    public void receive(Event event, Channel channel, Message message) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("Received : {}", event.getClass().getName());
            eventService.processEvent(event);

            // 처리 성공 -> ack
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("Failed to process event: {}", e.getMessage(), e);

            // 처리 실패 -> 재시도 위해 nack
            channel.basicNack(deliveryTag, false, true); // true: 다시 큐로
        }
    }

}
