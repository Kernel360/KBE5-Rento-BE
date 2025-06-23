package com.kbe5.api.domain.stream.service;


import com.kbe5.api.domain.stream.dto.StreamInfoRequest;
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
        // 이벤트를 보내기 위한 sseEmitter 객체 생성 0L로 설정하므로써 타임아웃은 없음 즉 무제한
        SseEmitter emitter = new SseEmitter(0L);
        // 서버에서 관리하는 SseEmitter 리스트에 등록
        emitters.add(emitter);
        // 클라이언트와의 연결이 정상적으로 종료됐을 때 → emitter 제거
        emitter.onCompletion(() -> removeEmitter(emitter, null));
        // 클라이언트가 오래 연결되어 있지 않거나 응답을 못 받을 경우 → emitter 제거
        emitter.onTimeout(()    -> removeEmitter(emitter, null));
        return emitter;
    }

    /**
     * 특정 driveId에만 관심 있는 클라이언트용
     */
    /*public SseEmitter subscribeForDrive(Long driveId) {
        SseEmitter emitter = new SseEmitter(0L);
        emitters.add(emitter);
        driveEmitters
                .computeIfAbsent(driveId, id -> new CopyOnWriteArrayList<>())
                .add(emitter);
        emitter.onCompletion(() -> removeEmitter(emitter, driveId));
        emitter.onTimeout(()    -> removeEmitter(emitter, driveId));
        return emitter;
    }*/

    /**
     * 전체 차량 이벤트 브로드캐스트
     */
    public void pushAll(StreamInfoRequest resquest) {
        // 전송 실패시 해당 구독자를 추가하는 공간입나다
        List<SseEmitter> dead = new ArrayList<>();

        // 구독한 사용자에게 데이터 전송을 합니다(리스트에 존재하는 모든 구독자)
        for (SseEmitter em : emitters) {
            try {
                em.send(resquest, MediaType.APPLICATION_JSON);
            } catch (Exception e) {
                dead.add(em);
            }
        }
        // 전송 실패한 사용자들은 제거 합니다
        emitters.removeAll(dead);
    }


    /**
     * 특정 차량 이벤트만 브로드캐스트
     * 이게 있는데 굳이 위의 메서드가 추가적으로 필요한가?
     */
    public void pushToDrive(Long driveId, StreamInfoRequest resquest) {
        // 보고 싶은 운행의 리스트를 생성합니다
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


    /**
     * 연결이 끊어진 구독자 제거
     */
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
