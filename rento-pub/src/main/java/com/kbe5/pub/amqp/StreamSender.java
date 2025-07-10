package com.kbe5.pub.amqp;

import com.kbe5.domain.device.entity.DeviceToken;
import com.kbe5.domain.event.dto.EventCommand;
import com.kbe5.domain.event.entity.CycleData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class StreamSender {

    private final RabbitTemplate rabbitTemplate;

    public void send(EventCommand.CycleEventCommand command, DeviceToken deviceToken) {

        List<CycleData> cycleData = command.toCycleInfoEntities(deviceToken);

        rabbitTemplate.convertAndSend("", "cycle-info-stream", cycleData);
        log.debug("실시간 관제 데이터 보내기: {}", cycleData);
    }
}