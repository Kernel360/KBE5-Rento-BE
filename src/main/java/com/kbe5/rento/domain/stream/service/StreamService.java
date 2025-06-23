package com.kbe5.rento.domain.stream.service;

import com.kbe5.rento.domain.stream.dto.StreamInfoResquest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
public class StreamService {
    
    // 전체 차량 리스너
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    // 차량ID별 리스너
    private final Map<Long, List<SseEmitter>> driveEmitters = new ConcurrentHashMap<>();

    /**
     * 전체 차량 위치를 받고 싶은 클라이언트용
     */
    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(0L);
        emitters.add(emitter);
        emitter.onCompletion(() -> removeEmitter(emitter, null));
        emitter.onTimeout(()    -> removeEmitter(emitter, null));
        return emitter;
    }

    /**
     * 특정 driveId에만 관심 있는 클라이언트용
     */
    public SseEmitter subscribeForDrive(Long driveId) {
        SseEmitter emitter = new SseEmitter(0L);
        emitters.add(emitter);
        driveEmitters
                .computeIfAbsent(driveId, id -> new CopyOnWriteArrayList<>())
                .add(emitter);
        emitter.onCompletion(() -> removeEmitter(emitter, driveId));
        emitter.onTimeout(()    -> removeEmitter(emitter, driveId));
        return emitter;
    }

    /**
     * 전체 차량 이벤트 브로드캐스트
     */
    public void pushAll(StreamInfoResquest resquest) {
        List<SseEmitter> dead = new ArrayList<>();
        for (SseEmitter em : emitters) {
            try {
                em.send(resquest, MediaType.APPLICATION_JSON);
            } catch (Exception e) {
                dead.add(em);
            }
        }
        emitters.removeAll(dead);
    }


    /**
     * 특정 차량 이벤트만 브로드캐스트
     */
    public void pushToDrive(Long driveId, StreamInfoResquest resquest) {
        List<SseEmitter> list = driveEmitters.getOrDefault(driveId, Collections.emptyList());
        List<SseEmitter> dead = new ArrayList<>();
        for (SseEmitter em : list) {
            try {
                em.send(resquest, MediaType.APPLICATION_JSON);
            } catch (Exception e) {
                dead.add(em);
            }
        }
        list.removeAll(dead);
    }

    private void removeEmitter(SseEmitter emitter, Long driveId) {
        emitters.remove(emitter);
        if (driveId != null) {
            List<SseEmitter> list = driveEmitters.get(driveId);
            if (list != null) {
                list.remove(emitter);
                if (list.isEmpty()) {
                    driveEmitters.remove(driveId);
                }
            }
        }
    }
}