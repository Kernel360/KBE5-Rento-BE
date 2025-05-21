package com.kbe5.rento.domain.company.dto.response;

import com.kbe5.rento.domain.company.entity.Company;

public record CompanyRegisterResponse(
        String name,
        String code
) {
    public static CompanyRegisterResponse from(Company company) {
        return new CompanyRegisterResponse(company.getName(), company.getCompanyCode());
    }
}
