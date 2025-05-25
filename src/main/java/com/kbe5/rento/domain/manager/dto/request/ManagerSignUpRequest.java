package com.kbe5.rento.domain.manager.dto.request;

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
}
