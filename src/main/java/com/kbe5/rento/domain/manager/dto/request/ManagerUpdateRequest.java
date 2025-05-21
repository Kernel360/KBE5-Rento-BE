package com.kbe5.rento.domain.manager.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ManagerUpdateRequest(

        @NotNull
        Long id,

        @NotBlank
        String name,

        @NotBlank
        String phone,

        @Email
        @NotBlank
        String email
) {
}
