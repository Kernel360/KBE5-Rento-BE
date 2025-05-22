package com.kbe5.rento.domain.company.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CompanyRegisterRequest(

        @NotNull
        int bizNumber,

        @NotBlank
        String name
) {

}
