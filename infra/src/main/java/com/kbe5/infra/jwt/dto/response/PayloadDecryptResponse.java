package com.kbe5.infra.jwt.dto.response;

import jakarta.validation.constraints.NotBlank;

public record PayloadDecryptResponse(

        @NotBlank
        String claim
) {
}
