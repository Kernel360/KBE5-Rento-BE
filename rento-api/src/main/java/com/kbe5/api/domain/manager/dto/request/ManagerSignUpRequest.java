package com.kbe5.api.domain.manager.dto.request;

import com.kbe5.rento.domain.manager.entity.Manager;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ManagerSignUpRequest(
        @NotBlank
        String loginId,

        @NotBlank
        String password,

        @NotBlank
        String name,

        @NotBlank
        String phone,

        @Email
        @NotBlank
        String email,

        @NotBlank
        String companyCode
) {
        public static Manager toEntity(ManagerSignUpRequest request) {
                return Manager.builder()
                        .loginId(request.loginId())
                        .password(request.password())
                        .name(request.name())
                        .phone(request.phone())
                        .email(request.email())
                        .companyCode(request.companyCode())
                        .build();
        }
}
