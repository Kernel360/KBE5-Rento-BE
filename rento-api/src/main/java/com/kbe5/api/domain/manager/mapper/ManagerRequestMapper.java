package com.kbe5.api.domain.manager.mapper;

import com.kbe5.api.domain.manager.dto.request.ManagerDeleteRequest;
import com.kbe5.api.domain.manager.dto.request.ManagerSignUpRequest;
import com.kbe5.api.domain.manager.dto.request.ManagerUpdateRequest;
import com.kbe5.domain.manager.dto.ManagerCommand;
import com.kbe5.infra.infrastructure.firebase.dto.UpdateFcmTokenRequest;

public class ManagerRequestMapper {

    public static ManagerCommand.Register toCommand(ManagerSignUpRequest request) {
        return ManagerCommand.Register.builder()
                .loginId(request.loginId())
                .password(request.password())
                .name(request.name())
                .phone(request.phone())
                .email(request.email())
                .companyCode(request.companyCode())
                .build();
    }

    public static ManagerCommand.Delete toCommand(ManagerDeleteRequest request) {
        return ManagerCommand.Delete.builder()
                .loginId(request.loginId())
                .password(request.password())
                .build();
    }

    public static ManagerCommand.Update toCommand(ManagerUpdateRequest request) {
        return ManagerCommand.Update.builder()
                .name(request.name())
                .phone(request.phone())
                .email(request.email())
                .build();
    }

    public static ManagerCommand.UpdateFcmToken toCommand(UpdateFcmTokenRequest request) {
        return ManagerCommand.UpdateFcmToken.builder()
                .fcmToken(request.token())
                .build();
    }
}
