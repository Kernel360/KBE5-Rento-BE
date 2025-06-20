package com.kbe5.api.domain.company.dto.response;

import com.kbe5.domain.company.entity.Company;

public record CompanyDetailResponse(
        Long id,
        String name,
        int bizNumber,
        String companyCode
) {
    public static CompanyDetailResponse fromEntity(Company company) {
        return new CompanyDetailResponse(company.getId(), company.getName(),
                company.getBizNumber(), company.getCompanyCode());
    }
}
