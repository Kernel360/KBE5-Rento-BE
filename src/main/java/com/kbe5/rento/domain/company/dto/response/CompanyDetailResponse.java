package com.kbe5.rento.domain.company.dto.response;

import com.kbe5.rento.domain.company.entity.Company;

public record CompanyDetailResponse(
        Long id,
        String name,
        int bizNumber,
        String companyCode
) {
    public static CompanyDetailResponse from(Company company) {
        return new CompanyDetailResponse(company.getId(), company.getName(),
                company.getBizNumber(), company.getCompanyCode());
    }
}
