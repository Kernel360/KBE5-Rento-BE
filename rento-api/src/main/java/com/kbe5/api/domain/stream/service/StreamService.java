package com.kbe5.api.domain.stream.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kbe5.domain.event.entity.CycleInfo;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class StreamService {

    // 매니저별 리스너만 관리 (전체 리스트 제거)
    private final Map<Long, List<SseEmitter>> managerEmitters = new ConcurrentHashMap<>();
    private final Map<Long, List<SseEmitter>> driveEmitters = new ConcurrentHashMap<>();

    // Heartbeat을 위한 스케줄러 -> 살아있는지 확인용으로 쓰레드 하나 잡아야함?
    private final ScheduledExecutorService heartbeatScheduler = Executors.newScheduledThreadPool(1);

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);


    /**
     * 연결 유지를 위해 핑 보내기
     */
    @PostConstruct
    public void initHeartbeat() {
        // 30초마다 heartbeat 전송(일반적임 길면 안좋고, 짧아도 안좋음) -> 30초는 타임 아웃이랑 겹칠수도 있어서 15~20초가 적당하다는데?
        heartbeatScheduler.scheduleAtFixedRate(this::sendHeartbeat, 15, 15, TimeUnit.SECONDS);
    }

    /**
     * 핑을 위한 스레드 정리
     */
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

    @RabbitListener(
            queues = "cycle-info-stream")
    public void receiveAndPush(@Payload CycleInfo cycleInfo) {
        log.debug("스트림 큐 수신: {}", cycleInfo);
        pushToAllManagers(cycleInfo);
    }

    /**
     * 매니저별 구독
     */
    public SseEmitter subscribe(Long managerId) {
        SseEmitter emitter = new SseEmitter(0L);

        // 매니저별 리스트에만 추가
        managerEmitters
                .computeIfAbsent(managerId, __ -> new CopyOnWriteArrayList<>())
                .add(emitter);
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

    /**
     * 모든 매니저에게 브로드캐스트 -> 추후 해당 업체의 매니저들에게만 주도록 수정
     */
    public void pushToAllManagers(CycleInfo cycleInfo) {
        managerEmitters.forEach((managerId, __) -> {
            pushToManager(managerId, cycleInfo);
        });
    }

    /**
     * 특정 매니저에게만 전송
     */
    public void pushToManager(Long managerId, CycleInfo cycleInfo) {
        List<SseEmitter> emitters = managerEmitters.get(managerId);
        if (emitters == null || emitters.isEmpty()) {
            return;
        }

        List<SseEmitter> deadEmitters = new ArrayList<>();

        for (SseEmitter emitter : emitters) {
            try {
                // 명시적으로 JSON 직렬화
                String jsonData = objectMapper.writeValueAsString(cycleInfo);

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

    /**
     * 연결 끊긴 매니저 emitter 제거
     */
    private void removeManagerEmitter(SseEmitter emitter, Long managerId) {
        List<SseEmitter> emitters = managerEmitters.get(managerId);
        if (emitters != null) {
            emitters.remove(emitter);
            if (emitters.isEmpty()) {
                managerEmitters.remove(managerId);
            }
        }
        log.debug("연결 끊긴 매니저: {}", managerId);
    }

    /**
     * 모든 연결된 클라이언트에게 heartbeat 전송
     */
    private void sendHeartbeat() {
        sendHeartbeatToManagers();
    }

    /**
     * 매니저들에게 heartbeat 전송
     */
    private void sendHeartbeatToManagers() {
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