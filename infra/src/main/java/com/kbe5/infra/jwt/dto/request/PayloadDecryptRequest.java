package com.kbe5.infra.jwt.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PayloadDecryptRequest(

        @NotBlank
        String claim
) {
}
