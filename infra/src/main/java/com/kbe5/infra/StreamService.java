package com.kbe5.domain.commonservice;


import com.kbe5.domain.event.entity.CycleInfo;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class StreamService {

    // 매니저별 리스너만 관리 (전체 리스트 제거)
    private final Map<Long, List<SseEmitter>> managerEmitters = new ConcurrentHashMap<>();
    private final Map<Long, List<SseEmitter>> driveEmitters = new ConcurrentHashMap<>();

    // Heartbeat을 위한 스케줄러 -> 살아있는지 확인용으로 쓰레드 하나 잡아야함?
    private final ScheduledExecutorService heartbeatScheduler = Executors.newScheduledThreadPool(1);

    @PostConstruct
    public void initHeartbeat() {
        // 30초마다 heartbeat 전송(일반적임 길면 안좋고, 짧아도 안좋음) -> 30초는 타임 아웃이랑 겹칠수도 있어서 15~20초가 적당하다는데?
        heartbeatScheduler.scheduleAtFixedRate(this::sendHeartbeat, 15, 15, TimeUnit.SECONDS);
    }

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

    /**
     * 매니저별 구독
     */
    public SseEmitter subscribe(Long managerId) {
        SseEmitter emitter = new SseEmitter(0L);

        // 매니저별 리스트에만 추가
        managerEmitters
                .computeIfAbsent(managerId, _ -> new CopyOnWriteArrayList<>())
                .add(emitter);
        log.info("Subscribed to manager id: {}", managerId);
        log.info("Subscribed to manager: {}", managerEmitters);
        // 연결 해제 시 정리
        emitter.onCompletion(() -> removeManagerEmitter(emitter, managerId));
        emitter.onTimeout(() -> removeManagerEmitter(emitter, managerId));
        emitter.onError(throwable -> {
            log.error("SSE error for manager {}: {}", managerId, throwable.getMessage());
            removeManagerEmitter(emitter, managerId);
        });

        // 초기 연결 확인 메시지 전송
        try {
            emitter.send(SseEmitter.event()
                    .name("connected")
                    .data("Connected to stream"));
        } catch (Exception e) {
            log.error("Failed to send initial message to manager {}: {}", managerId, e.getMessage());
            removeManagerEmitter(emitter, managerId);
        }

        return emitter;
    }

    /**
     * 특정 drive 구독
     */
    public SseEmitter subscribeForDrive(Long driveId) {
        SseEmitter emitter = new SseEmitter(0L);

        driveEmitters
                .computeIfAbsent(driveId, _ -> new CopyOnWriteArrayList<>())
                .add(emitter);

        emitter.onCompletion(() -> removeDriveEmitter(emitter, driveId));
        emitter.onTimeout(() -> removeDriveEmitter(emitter, driveId));
        emitter.onError(throwable -> {
            log.error("SSE error for drive {}: {}", driveId, throwable.getMessage());
            removeDriveEmitter(emitter, driveId);
        });

        return emitter;
    }

    /**
     * 모든 매니저에게 브로드캐스트 -> 추후 해당 업체의 매니저들에게만 주도록 수정
     */
    public void pushToAllManagers(CycleInfo cycleInfo) {
        log.info("매니저 인포 사이즈: {}", managerEmitters);
        managerEmitters.forEach((managerId, _) -> {
            pushToManager(managerId, cycleInfo);
        });
    }

    /**
     * 특정 매니저에게만 전송
     */
    public void pushToManager(Long managerId, CycleInfo cycleInfo) {
        log.info("매니저 인포 사이즈: {}", managerEmitters.get(managerId));
        List<SseEmitter> emitters = managerEmitters.get(managerId);
        if (emitters == null || emitters.isEmpty()) {
            return;
        }

        List<SseEmitter> deadEmitters = new ArrayList<>();

        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event()
                        .name("cycle-info")
                        .data(cycleInfo, MediaType.APPLICATION_JSON));
                log.debug("SSE sent to manager {}: {}", managerId, cycleInfo);
            } catch (IOException e) {
                log.warn("Failed to send SSE to manager {}: {}", managerId, e.getMessage());
                deadEmitters.add(emitter);
            } catch (Exception e) {
                log.error("Unexpected error sending SSE to manager {}: {}", managerId, e.getMessage());
                deadEmitters.add(emitter);
            }
        }

        // 실패한 emitter들 제거
        emitters.removeAll(deadEmitters);
        if (emitters.isEmpty()) {
            managerEmitters.remove(managerId);
        }
    }

    /**
     * 특정 drive에 관심있는 클라이언트들에게 전송
     */
    public void pushToDrive(Long driveId, CycleInfo cycleInfo) {
        List<SseEmitter> emitters = driveEmitters.get(driveId);
        if (emitters == null || emitters.isEmpty()) {
            return;
        }

        List<SseEmitter> deadEmitters = new ArrayList<>();

        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event()
                        .name("drive-cycle-info")
                        .data(cycleInfo, MediaType.APPLICATION_JSON));
            } catch (IOException e) {
                log.warn("Failed to send SSE to drive {}: {}", driveId, e.getMessage());
                deadEmitters.add(emitter);
            } catch (Exception e) {
                log.error("Unexpected error sending SSE to drive {}: {}", driveId, e.getMessage());
                deadEmitters.add(emitter);
            }
        }

        emitters.removeAll(deadEmitters);
        if (emitters.isEmpty()) {
            driveEmitters.remove(driveId);
        }
    }

    /**
     * 매니저 emitter 제거
     */
    private void removeManagerEmitter(SseEmitter emitter, Long managerId) {
        List<SseEmitter> emitters = managerEmitters.get(managerId);
        if (emitters != null) {
            emitters.remove(emitter);
            if (emitters.isEmpty()) {
                managerEmitters.remove(managerId);
            }
        }
        log.debug("Removed SSE connection for manager: {}", managerId);
    }

    /**
     * Drive emitter 제거
     */
    private void removeDriveEmitter(SseEmitter emitter, Long driveId) {
        List<SseEmitter> emitters = driveEmitters.get(driveId);
        if (emitters != null) {
            emitters.remove(emitter);
            if (emitters.isEmpty()) {
                driveEmitters.remove(driveId);
            }
        }
        log.debug("Removed SSE connection for drive: {}", driveId);
    }

    /**
     * 현재 연결된 클라이언트 수 조회
     */
    public int getManagerConnectionCount() {
        return managerEmitters.values().stream()
                .mapToInt(List::size)
                .sum();
    }

    public int getDriveConnectionCount() {
        return driveEmitters.values().stream()
                .mapToInt(List::size)
                .sum();
    }

    /**
     * 모든 연결된 클라이언트에게 heartbeat 전송
     */
    private void sendHeartbeat() {
        sendHeartbeatToManagers();
        sendHeartbeatToDrives();
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
                    log.debug("Heartbeat failed for manager {}: {}", managerId, e.getMessage());
                    deadEmitters.add(emitter);
                } catch (Exception e) {
                    log.warn("Unexpected error during heartbeat for manager {}: {}", managerId, e.getMessage());
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
            log.info("Removed {} inactive manager connections during heartbeat", managersToRemove.size());
        }
    }

    /**
     * Drive 구독자들에게 heartbeat 전송
     */
    private void sendHeartbeatToDrives() {
        List<Long> drivesToRemove = new ArrayList<>();

        driveEmitters.forEach((driveId, emitters) -> {
            List<SseEmitter> deadEmitters = new ArrayList<>();

            for (SseEmitter emitter : emitters) {
                try {
                    emitter.send(SseEmitter.event()
                            .name("heartbeat")
                            .data("ping"));
                } catch (IOException e) {
                    log.debug("Heartbeat failed for drive {}: {}", driveId, e.getMessage());
                    deadEmitters.add(emitter);
                } catch (Exception e) {
                    log.warn("Unexpected error during heartbeat for drive {}: {}", driveId, e.getMessage());
                    deadEmitters.add(emitter);
                }
            }

            emitters.removeAll(deadEmitters);
            if (emitters.isEmpty()) {
                drivesToRemove.add(driveId);
            }
        });

        drivesToRemove.forEach(driveEmitters::remove);

        if (!drivesToRemove.isEmpty()) {
            log.info("Removed {} inactive drive connections during heartbeat", drivesToRemove.size());
        }
    }
}