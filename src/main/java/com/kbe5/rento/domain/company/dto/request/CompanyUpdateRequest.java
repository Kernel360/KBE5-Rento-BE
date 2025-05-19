package com.kbe5.rento.domain.company.dto.request;

public record CompanyUpdateRequest(
        Long id,
        int bizNumber,
        String name
) {
}
