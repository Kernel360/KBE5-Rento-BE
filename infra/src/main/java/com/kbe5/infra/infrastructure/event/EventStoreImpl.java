package com.kbe5.infra.infrastructure.event;

import com.kbe5.domain.event.entity.CycleData;
import com.kbe5.domain.event.entity.Event;
import com.kbe5.domain.event.service.EventStore;
import com.kbe5.infra.infrastructure.event.repository.CycleInfoRepository;
import com.kbe5.infra.infrastructure.event.repository.EventRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventStoreImpl implements EventStore {

    private final EventRepository eventRepository;
    private final CycleInfoRepository cycleInfoRepository;

    @Override
    public void store(Event event) {
        eventRepository.save(event);
    }

    @Override
    public void bulkInsert(List<CycleData> cycleData) {
        cycleInfoRepository.bulkInsert(cycleData);
    }
}
