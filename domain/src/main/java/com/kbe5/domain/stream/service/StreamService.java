package com.kbe5.domain.stream.service;

import com.kbe5.domain.event.entity.CycleData;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface StreamService {

    void initHeartbeat();
    void cleanup();
    void receiveAndPush(CycleData cycleData);
    Long getCompanyIdByMdn(Long mdn);
    SseEmitter subscribe(Long managerId, Long companyId);
    void pushToCompanyManagers(CycleData cycleData, Long companyId);
    void pushToManager(Long managerId, CycleData cycleData);
    void removeManagerEmitter(SseEmitter emitter, Long managerId);
    void sendHeartbeat();
    void sendHeartbeatToManagers() ;
}