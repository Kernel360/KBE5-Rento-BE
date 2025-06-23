package com.kbe5.api.domain.company.dto.request;

import com.kbe5.domain.company.entity.Company;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CompanyRegisterRequest(

        @NotNull
        int bizNumber,

        @NotBlank
        String name
) {
        public static Company toEntity(CompanyRegisterRequest request) {
                return Company.builder()
                        .bizNumber(request.bizNumber())
                        .name(request.name())
                        .build();
        }
}
