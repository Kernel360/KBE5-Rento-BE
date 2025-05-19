package com.kbe5.rento.domain.company.dto.response;

public record CompanyDetailResponse(
        Long id,
        String name,
        int bizNumber,
        String companyCode
) {
}
