package com.kbe5.domain.stream.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kbe5.domain.event.entity.CycleData;
import com.kbe5.domain.event.entity.CycleEvent;
import com.kbe5.domain.event.entity.Event;
import com.kbe5.domain.stream.service.dto.CycleInfo;
import com.kbe5.domain.vehicle.service.VehicleService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StreamServiceImpl implements StreamService {

    // 매니저별 리스너만 관리 (전체 리스트 제거)
    private final Map<Long, List<SseEmitter>> managerEmitters = new ConcurrentHashMap<>();
    private final Map<Long, Long> managerCompanyEmitters = new ConcurrentHashMap<>();

    // 캐싱 용도
    private final Map<Long, Long> vehicleCompanyCache = new ConcurrentHashMap<>();
    private final VehicleService vehicleService;

    // Heartbeat을 위한 스케줄러 -> 살아있는지 확인용으로 쓰레드 하나 잡아야함?
    private final ScheduledExecutorService heartbeatScheduler = Executors.newScheduledThreadPool(1);

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);


    @Override
    @PostConstruct
    public void initHeartbeat() {
        // 30초마다 heartbeat 전송(일반적임 길면 안좋고, 짧아도 안좋음) -> 30초는 타임 아웃이랑 겹칠수도 있어서 15~20초가 적당하다는데?
        heartbeatScheduler.scheduleAtFixedRate(this::sendHeartbeat, 15, 15, TimeUnit.SECONDS);
    }

    @Override
    @PreDestroy
    public void cleanup() {
        heartbeatScheduler.shutdown();
        try {
            if (!heartbeatScheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                heartbeatScheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            heartbeatScheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    @Override
    @RabbitListener(
            queues = "cycle-info-stream")
    public void receiveAndPush(Event event) {

        CycleEvent cycleEvent = (CycleEvent) event;

        List<CycleData> cycleDataList = cycleEvent.getCycleData();

        Long companyId = getCompanyIdByMdn(cycleEvent.getMdn());

        if (companyId != null) {
            pushToCompanyManagers(cycleDataList, companyId);
        } else {
            log.warn("업체를 찾을 수 없는 차량 mdn: {}", cycleEvent.getMdn());
        }
    }

    @Override
    public Long getCompanyIdByMdn(Long mdn) {
        return vehicleCompanyCache.computeIfAbsent(mdn, key -> {
            try {
                Long companyId = vehicleService.getCompanyIdByMdn(key);
                log.debug("차량 mdn {} -> 업체 ID {} 캐시 저장", key, companyId);
                return companyId;
            } catch (Exception e) {
                log.error("차량 mdn {}로 업체 조회 실패: {}", key, e.getMessage());
                return null;
            }
        });
    }

    @Override
    public SseEmitter subscribe(Long managerId, Long companyId) {
        SseEmitter emitter = new SseEmitter(0L);

        // 매니저별 리스트에만 추가
        managerEmitters
                .computeIfAbsent(managerId, __ -> new CopyOnWriteArrayList<>())
                .add(emitter);

        managerCompanyEmitters.put(managerId, companyId);

        log.info("매니저 id: {}", managerId);
        log.info("구독 중인 매니저: {}", managerEmitters);
        // 연결 해제 시 정리
        emitter.onCompletion(() -> removeManagerEmitter(emitter, managerId));
        emitter.onTimeout(() -> removeManagerEmitter(emitter, managerId));
        emitter.onError(throwable -> {
            log.error("연결 끊긴 매니저 {}: {}", managerId, throwable.getMessage());
            removeManagerEmitter(emitter, managerId);
        });

        // 초기 연결 확인 메시지 전송
        try {
            emitter.send(SseEmitter.event()
                    .name("connected")
                    .data("Connected to stream"));
        } catch (Exception e) {
            log.error("발송 안된 매니저 {}: {}", managerId, e.getMessage());
            removeManagerEmitter(emitter, managerId);
        }

        return emitter;
    }

    @Override
    public void pushToCompanyManagers(List<CycleData> cycleDataList, Long companyId) {
        log.debug("업체 {} CycleData 전송 시작", companyId);
        log.debug("현재 매니저-업체 매핑: {}", managerCompanyEmitters);

        List<Long> targetManagers = managerCompanyEmitters.entrySet().stream()
                .filter(entry -> companyId.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        log.debug("대상 매니저들: {}", targetManagers);

        targetManagers.forEach(managerId -> pushToManager(managerId, cycleDataList));
    }

    @Override
    public void pushToManager(Long managerId, List<CycleData> cycleDataList) {
        List<SseEmitter> emitters = managerEmitters.get(managerId);
        if (emitters == null || emitters.isEmpty()) {
            return;
        }

        List<SseEmitter> deadEmitters = new ArrayList<>();

       // CycleInfo cycleInfo = CycleInfo.fromCycleInfo(cycleData);

        for (SseEmitter emitter : emitters) {
            try {
                // 명시적으로 JSON 직렬화
                String jsonData = objectMapper.writeValueAsString(cycleDataList);

                emitter.send(SseEmitter.event()
                        .name("cycle-info")
                        .data(jsonData, MediaType.APPLICATION_JSON));
                log.debug("매니저에게 발송 {}: {}", managerId, jsonData); // JSON 확인용
            } catch (IOException e) {
                log.warn("발송 실패 매니저 {}: {}", managerId, e.getMessage());
                deadEmitters.add(emitter);
            } catch (Exception e) {
                log.error("서버 오류로 인한 발송 실패 매니저 {}: {}", managerId, e.getMessage());
                deadEmitters.add(emitter);
            }
        }

        // 실패한 매니저들 제거
        emitters.removeAll(deadEmitters);
        if (emitters.isEmpty()) {
            managerEmitters.remove(managerId);
        }
    }

    @Override
    public void removeManagerEmitter(SseEmitter emitter, Long managerId) {
        List<SseEmitter> emitters = managerEmitters.get(managerId);
        if (emitters != null) {
            emitters.remove(emitter);
            if (emitters.isEmpty()) {
                managerEmitters.remove(managerId);
            }
        }
        log.debug("연결 끊긴 매니저: {}", managerId);
    }

    @Override
    public void sendHeartbeat() {
        sendHeartbeatToManagers();
    }

    @Override
    public void sendHeartbeatToManagers() {
        List<Long> managersToRemove = new ArrayList<>();

        managerEmitters.forEach((managerId, emitters) -> {
            List<SseEmitter> deadEmitters = new ArrayList<>();

            for (SseEmitter emitter : emitters) {
                try {
                    emitter.send(SseEmitter.event()
                            .name("heartbeat")
                            .data("ping"));
                } catch (IOException e) {
                    log.debug("핑 보내기 실패 매니저 {}: {}", managerId, e.getMessage());
                    deadEmitters.add(emitter);
                } catch (Exception e) {
                    log.warn("서버 오류로 핑보내기 실패 매니저 {}: {}", managerId, e.getMessage());
                    deadEmitters.add(emitter);
                }
            }

            // 실패한 emitter들 제거
            emitters.removeAll(deadEmitters);
            if (emitters.isEmpty()) {
                managersToRemove.add(managerId);
            }
        });

        // 빈 매니저 엔트리 제거
        managersToRemove.forEach(managerEmitters::remove);

        if (!managersToRemove.isEmpty()) {
            log.info("핑 안보내지는 매니저 size { }", managersToRemove.size());
        }
    }
}
