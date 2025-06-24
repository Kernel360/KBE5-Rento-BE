package com.kbe5.api.domain.manager.dto.request;

import com.kbe5.domain.manager.entity.Manager;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ManagerSignUpRequest(
        @NotBlank
        @Size(min = 4, message = "아이디는 4자리 이상이어야합니다.")
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
