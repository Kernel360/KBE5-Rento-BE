package com.kbe5.rento.domain.company.dto.request;

public record CompanyDeleteRequest(
        String companyCode,
        String managerId,
        String managerPassword
) {
}
