package com.kbe5.api.domain.company.dto.response;

public record CompanyResponse(
        Long id,
        String name,
        int bizNumber,
        String companyCode
) {
}
