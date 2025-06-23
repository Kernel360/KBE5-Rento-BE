package com.kbe5.adapter.service;


import com.kbe5.api.domain.stream.service.StreamService;
import com.kbe5.domain.event.entity.CycleEvent;
import com.kbe5.domain.event.entity.CycleInfo;
import com.kbe5.domain.event.entity.Event;
import com.kbe5.domain.event.enums.EventType;
import com.kbe5.domain.event.repository.CycleInfoRepository;
import com.kbe5.domain.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CycleInfoHandler implements EventHandler {

    private final EventRepository eventRepository;

    private final CycleInfoRepository cycleInfoRepository;

    private final StreamService streamService;

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

            cycleInfoList.forEach(info -> {
                StreamInfoRequest request = StreamInfoRequest.of(info);
                streamService.pushAll(request); // 전체 브로드캐스트
            });
        }
    }

    private boolean isNotNullAndNotEmpty(List<CycleInfo> cycleInfo) {
        return cycleInfo != null && !cycleInfo.isEmpty();
    }
}
