package com.kbe5.api.domain.firebase.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.kbe5.api.domain.firebase.dto.TokenNotificationRequest;
import com.kbe5.domain.drive.entity.Drive;
import com.kbe5.domain.drive.entity.DriveStatus;
import com.kbe5.domain.manager.entity.Manager;
import com.kbe5.domain.manager.respository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
            if (token == null) {
                log.warn("FCM 토큰이 null 입니다. 메시지를 생성하지 않습니다.");
                continue;  // null이면 건너뜀
            }

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

    public void getDrive(Drive drive) {
        // 자동차 번호
        String vehicleNumber = drive.getVehicle().getInfo().vehicleNumber();
        // 회사의 모든 매니저
        List<Manager> managers = managerRepository.findAllByCompany(drive.getVehicle().getCompany());
        // 현재 시간
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // 알림 전송
        if (drive.getDriveStatus() == DriveStatus.DRIVING || drive.getDriveStatus() == DriveStatus.COMPLETED) {
            TokenNotificationRequest notificationRequest = buildNotificationRequest(drive.getDriveStatus(), vehicleNumber, now);
            send(notificationRequest, managers);
        }
    }

    private TokenNotificationRequest buildNotificationRequest(DriveStatus status, String vehicleNumber, String now) {
        String title;
        String body;
        String url = "http://localhost:3000"; // TODO: 지도 URL 완성되면 수정

        if (status == DriveStatus.DRIVING) {
            title = "운행 시작";
            body = String.format("%s의 운행이 시작되었습니다.\n%s", vehicleNumber, now);
        } else {
            title = "운행 종료";
            body = String.format("%s의 운행이 종료되었습니다.\n%s", vehicleNumber, now);
        }

        return new TokenNotificationRequest(title, body, url);
    }
}

