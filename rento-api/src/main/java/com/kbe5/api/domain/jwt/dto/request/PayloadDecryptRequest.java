package com.kbe5.api.domain.jwt.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PayloadDecryptRequest(

        @NotBlank
        String claim
) {
}
