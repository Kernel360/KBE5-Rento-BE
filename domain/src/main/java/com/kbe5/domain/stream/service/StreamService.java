package com.kbe5.domain.stream.service;

import com.kbe5.domain.event.entity.CycleData;
import com.kbe5.domain.event.entity.Event;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

public interface StreamService {

    void initHeartbeat();
    void cleanup();
    void receiveAndPush(Event event);
    Long getCompanyIdByMdn(Long mdn);
    SseEmitter subscribe(Long managerId, Long companyId);
    void pushToCompanyManagers(List<CycleData> cycleDataList, Long companyId);
    void pushToManager(Long managerId, List<CycleData> cycleDataList);
    void removeManagerEmitter(SseEmitter emitter, Long managerId);
    void sendHeartbeat();
    void sendHeartbeatToManagers() ;
}