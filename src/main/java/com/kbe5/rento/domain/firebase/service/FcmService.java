package com.kbe5.rento.domain.firebase.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.common.exception.ErrorType;
import com.kbe5.rento.domain.firebase.dto.TokenNotificationRequest;
import com.kbe5.rento.domain.manager.entity.Manager;
import com.kbe5.rento.domain.manager.respository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmService {

    private final ManagerRepository managerRepository;

    public void send(TokenNotificationRequest tokenNotificationRequest, List<Manager> managers) {
        List<String> tokens = managers.stream()
                .map(Manager::getFcmToken)
                .toList();

        //실패한 토큰 저장하는 List
        List<String> failedTokens = new ArrayList<>();

        for (String token : tokens) {
            Message message = Message.builder()
                    .setToken(token)
                    .putData("title", tokenNotificationRequest.title())
                    .putData("body", tokenNotificationRequest.content())
                    .putData("click_action", tokenNotificationRequest.url())
                    .build();


            log.info("Sending message to firebase: {}", message);

            try {
                FirebaseMessaging.getInstance().send(message);
            } catch (FirebaseMessagingException e) {
                failedTokens.add(token);
                log.error("알림 전송에 실패한 토큰: " + token, e);
            }
        }
        //실패한 토큰이 있을 시에
        if (!failedTokens.isEmpty()) {
            failedTokens.forEach(failedToken -> {
                Optional<Manager> invalidmanager = managerRepository.findByFcmToken(failedToken);
                invalidmanager.ifPresent(manager -> {
                    manager.assignFcmToken(null);
                });
            });
        }
    }
}
