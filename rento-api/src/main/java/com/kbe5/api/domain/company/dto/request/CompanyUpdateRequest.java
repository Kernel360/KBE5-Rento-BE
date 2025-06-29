package com.kbe5.api.domain.company.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CompanyUpdateRequest(
        @NotNull
        int bizNumber,

        @NotBlank
        String name
) {
}
