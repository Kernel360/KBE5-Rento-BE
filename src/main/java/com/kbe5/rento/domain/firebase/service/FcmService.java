package com.kbe5.rento.domain.firebase.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.kbe5.rento.domain.firebase.dto.TokenNotificationRequest;
import com.kbe5.rento.domain.manager.entity.Manager;
import com.kbe5.rento.domain.manager.respository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmService {

    private final ManagerRepository managerRepository;

    public void send(TokenNotificationRequest tokenNotificationRequest, Manager manager) {
        String token = manager.getFcmToken();

        Message message = Message.builder()
                .setToken(token)
                .setNotification(Notification.builder()
                        .setTitle(tokenNotificationRequest.title())
                        .setBody(tokenNotificationRequest.content())
                        .build())
                .putData("click_action", tokenNotificationRequest.url())
                .build();

        log.info("Sending message to firebase: {}", message);

        try{
            FirebaseMessaging.getInstance().send(message);
        } catch(FirebaseMessagingException e) {
            log.error("알림 전송에 실패한 토큰: " + token, e);
            //에러가 터지면 올바르지않은 토큰이니 null 처리 -> todo: null을 넣어도 괜찮은가?
            manager.assignFcmToken(null);
            managerRepository.save(manager);
        }
    }
}
