package com.kbe5.adapter.service;


import com.kbe5.domain.commonservice.StreamService;
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

    }

    private boolean isNotNullAndNotEmpty(List<CycleInfo> cycleInfo) {
        return cycleInfo != null && !cycleInfo.isEmpty();
    }
}
