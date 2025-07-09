package com.kbe5.domain.firebase.service;

import com.kbe5.domain.firebase.dto.FcmCommand;
import com.kbe5.domain.manager.entity.Manager;

import java.util.List;

public interface FirebaseSender {
    void send(FcmCommand.NotificationRequest request, List<Manager> managers);
}
