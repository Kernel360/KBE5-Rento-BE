package com.kbe5.domain.stream.service;

import com.kbe5.domain.event.entity.CycleInfo;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface StreamService {

    void initHeartbeat();
    void cleanup();
    void receiveAndPush(CycleInfo cycleInfo);
    Long getCompanyIdByMdn(Long mdn);
    SseEmitter subscribe(Long managerId, Long companyId);
    void pushToCompanyManagers(CycleInfo cycleInfo, Long companyId);
    void pushToManager(Long managerId, CycleInfo cycleInfo);
    void removeManagerEmitter(SseEmitter emitter, Long managerId);
    void sendHeartbeat();
    void sendHeartbeatToManagers() ;
}