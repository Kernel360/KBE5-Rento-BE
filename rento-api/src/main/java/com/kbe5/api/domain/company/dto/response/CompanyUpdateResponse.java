package com.kbe5.api.domain.company.dto.response;


import com.kbe5.domain.company.entity.Company;

public record CompanyUpdateResponse(
        int bizNumber,
        String name
) {
    public static CompanyUpdateResponse fromEntity(Company company) {
        return new CompanyUpdateResponse(company.getBizNumber(), company.getName());
    }
}
