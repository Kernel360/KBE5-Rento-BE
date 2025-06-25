package com.kbe5.api.domain.manager.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ManagerLoginRequest(

        @NotBlank
        String loginId,

        @NotBlank
        String password
) {
}
