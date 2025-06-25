package com.kbe5.api.domain.jwt.dto.response;

import jakarta.validation.constraints.NotBlank;

public record PayloadDecryptResponse(

        @NotBlank
        String claim
) {
}
