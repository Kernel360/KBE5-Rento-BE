package com.kbe5.adapter.amqp;

import com.kbe5.domain.commonservice.firebase.service.FcmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@RabbitListener(queues = "notification")
public class NotificationReceiver {
    private final FcmService fcmService;

    @RabbitHandler
    public void receive(Long driveId){

        log.info("Received Notification From Queue driveId: {}", driveId);

        fcmService.getDrive(driveId);
    }
}
