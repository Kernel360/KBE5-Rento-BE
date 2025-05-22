package com.kbe5.rento.domain.company.dto.response;

import com.kbe5.rento.domain.company.entity.Company;

public record CompanyResponse(
        Long id,
        String name,
        int bizNumber,
        String companyCode
) {
    public static CompanyResponse from(Company company) {
        return new CompanyResponse(
                company.getId(),
                company.getName(),
                company.getBizNumber(),
                company.getCompanyCode()
        );
    }
}
