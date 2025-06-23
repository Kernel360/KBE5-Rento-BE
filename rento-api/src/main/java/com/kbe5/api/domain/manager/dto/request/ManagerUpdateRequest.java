package com.kbe5.api.domain.manager.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ManagerUpdateRequest(
        @NotBlank
        String name,

        @NotBlank
        String phone,

        @Email
        @NotBlank
        String email
) {
}
