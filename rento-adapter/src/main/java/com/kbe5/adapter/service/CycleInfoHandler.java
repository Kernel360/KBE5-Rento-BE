package com.kbe5.adapter.service;


import com.kbe5.adapter.amqp.StreamSender;
import com.kbe5.domain.event.entity.CycleEvent;
import com.kbe5.domain.event.entity.CycleInfo;
import com.kbe5.domain.event.entity.Event;
import com.kbe5.domain.event.enums.EventType;
import com.kbe5.domain.event.repository.CycleInfoRepository;
import com.kbe5.domain.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class CycleInfoHandler implements EventHandler {

    private final EventRepository eventRepository;

    private final CycleInfoRepository cycleInfoRepository;

    private final StreamSender streamSender;

    @Override
    public EventType getEventType() {
        return EventType.CYCLE_INFO;
    }

    @Override
    public void handle(Event event) {

        CycleEvent cycleEvent = (CycleEvent) event;
        List<CycleInfo> cycleInfo = cycleEvent.getCycleInfos();

        eventRepository.save(cycleEvent);

        if (isNotNullAndNotEmpty(cycleInfo)){
            cycleInfoRepository.bulkInsert(cycleInfo);

            // 스트리밍 큐에 메시지 발행
            log.info("스트림 큐로 메시지 발행 시작");
            cycleInfo.forEach(streamSender::send);
        }
    }

    private boolean isNotNullAndNotEmpty(List<CycleInfo> cycleInfo) {
        return cycleInfo != null && !cycleInfo.isEmpty();
    }
}
