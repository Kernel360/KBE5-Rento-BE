package com.kbe5.adapter.amqp;

import com.kbe5.domain.event.entity.CycleInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StreamSender {

    private final RabbitTemplate rabbitTemplate;

    public void send(CycleInfo ci) {
        rabbitTemplate.convertAndSend("", "실시간 관제", ci);
        log.debug("실시간 관제 데이터 보내기: {}", ci);
    }
}