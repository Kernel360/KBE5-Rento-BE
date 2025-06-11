package com.kbe5.rento.domain.event.amqp;

import com.kbe5.rento.domain.event.entity.CycleInfo;
import com.kbe5.rento.domain.event.entity.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventSender {

    private final RabbitTemplate template;

    private final Queue queue;

    public void send(Event event, Long mdn) {
        event.validateMdnMatch(mdn);

        template.convertAndSend(queue.getName(), event);
    }
//
//    public void sendEventAndCycleInfo(Event event, List<CycleInfo> cycleInfoList) {
//        // 1. 이벤트 객체를 "event" 큐로
//        template.convertAndSend("event", event);
//
//        // 2. 주기정보 리스트를 "cycle-info" 큐로
//        template.convertAndSend("cycle-info", cycleInfoList);
//    }
}
