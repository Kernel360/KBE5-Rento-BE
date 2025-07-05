package com.kbe5.api.domain.manager.dto.request;

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
){
}
