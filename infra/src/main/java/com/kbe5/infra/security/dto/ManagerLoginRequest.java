package com.kbe5.infra.security.dto;

import jakarta.validation.constraints.NotBlank;

public record ManagerLoginRequest(

        @NotBlank
        String loginId,

        @NotBlank
        String password
) {
}
