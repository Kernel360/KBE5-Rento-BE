package com.kbe5.api.domain.company.service.dto.response;


import com.kbe5.domain.company.entity.Company;

public record CompanyRegisterResponse(
        String name,
        String code
) {
    public static CompanyRegisterResponse fromEntity(Company company) {
        return new CompanyRegisterResponse(company.getName(), company.getCompanyCode());
    }
}
