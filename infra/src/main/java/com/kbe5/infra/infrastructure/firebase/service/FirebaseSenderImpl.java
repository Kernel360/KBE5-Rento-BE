package com.kbe5.infra.infrastructure.firebase.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.kbe5.domain.exception.DomainException;
import com.kbe5.domain.exception.ErrorType;
import com.kbe5.domain.firebase.dto.FcmCommand;
import com.kbe5.domain.firebase.service.FirebaseSender;
import com.kbe5.domain.manager.entity.Manager;
import com.kbe5.domain.manager.service.ManagerReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class FirebaseSenderImpl implements FirebaseSender {

    private final FirebaseMessaging firebaseMessaging;
    private final ManagerReader managerReader;


    @Override
    @Retryable(
            retryFor = FirebaseMessagingException.class,
            backoff = @Backoff(delay = 1000, multiplier = 2)
    ) //재시도 간격 1초, 지수백오프 2배 (1초, 2초, 4초 기다리기), 기본 시도횟수 3번(디폴트)
    public void send(FcmCommand.NotificationRequest request, List<Manager> managers) {
        List<String> tokens = managers.stream()
                .map(Manager::getFcmToken)
                .toList();

        List<String> failedTokens = new ArrayList<>();

        for(String token : tokens) {
            if (token == null) {
                log.warn("FCM 토큰이 null 입니다. 메시지를 생성하지 않습니다.");
                continue;  // null이면 건너뜀
            }

            Message message = Message.builder()
                    .setToken(token)
                    .putData("title", request.getTitle())
                    .putData("body", request.getBody())
                    .putData("click_action", request.getUrl())
                    .build();

            log.info("Firebase에 메시지 보내기: {}" , message);

            try{
                firebaseMessaging.send(message);
            } catch(FirebaseMessagingException e){
                failedTokens.add(token);
                log.error("알림 전송에 실패한 토큰: " + token, e);
            }

            if(!failedTokens.isEmpty()) {
                failedTokens.forEach(failedToken -> {
                    Optional<Manager> invalidManagers = managerReader.findByFcmToken(failedToken);
                    invalidManagers.ifPresent(manager -> {
                        manager.assignFcmToken(null);
                    });
                });
            }
        }
    }

    @Recover //호출을 따로 하지 않아도 Spring이 자동 호출
    public void recover(FirebaseMessagingException e) {
        throw new DomainException(ErrorType.FCM_FAILED);
    }
}
