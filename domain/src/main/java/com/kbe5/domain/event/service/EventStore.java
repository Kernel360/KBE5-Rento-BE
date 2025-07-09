package com.kbe5.domain.event.service;

import com.kbe5.domain.event.entity.CycleInfo;
import com.kbe5.domain.event.entity.Event;
import java.util.List;

public interface EventStore {

    void store(Event event);

    void bulkInsert(List<CycleInfo> cycleInfo);
}
