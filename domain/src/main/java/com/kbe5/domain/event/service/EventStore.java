package com.kbe5.domain.event.service;

import com.kbe5.domain.event.entity.CycleData;
import com.kbe5.domain.event.entity.Event;
import java.util.List;

public interface EventStore {

    void store(Event event);

    void bulkInsert(List<CycleData> cycleData);
}
