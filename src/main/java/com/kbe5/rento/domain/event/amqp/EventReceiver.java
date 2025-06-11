package com.kbe5.rento.domain.event.amqp;

import com.kbe5.rento.domain.event.entity.Event;
import com.kbe5.rento.domain.event.service.CycleInfoService;
import com.kbe5.rento.domain.event.service.EventService;
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

    private final CycleInfoService cycleInfoService;

    //event 발생시 기본
    @RabbitHandler
    public void receive(Event event) {

        log.info("Received : {}", event);
        eventService.saveEvent(event);
    }

    //주기 정보 이벤트
//    @RabbitHandler
//    public void receiveCycleInfo(List<CycleInfo> cycleInfo) {
//
//        log.info("cycleInfo size : {}", cycleInfo.size());
//        cycleInfoService.saveCycleInfo(cycleInfo);
//    }
}
