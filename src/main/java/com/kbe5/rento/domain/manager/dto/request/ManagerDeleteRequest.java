package com.kbe5.rento.domain.manager.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ManagerDeleteRequest(
        @NotBlank
        String loginId,

        @NotBlank
        String password
) {
}
