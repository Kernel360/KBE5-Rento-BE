package com.kbe5.api.domain.company.service.dto.response;


import com.kbe5.domain.company.entity.Company;

import java.util.List;

public record CompanyResponse(
        Long id,
        String name,
        int bizNumber,
        String companyCode
) {
    public static CompanyResponse fromEntity(Company company) {
        return new CompanyResponse(
                company.getId(),
                company.getName(),
                company.getBizNumber(),
                company.getCompanyCode()
        );
    }

    public static List<CompanyResponse> fromEntity(List<Company> managerList) {
        return managerList.stream()
                .map(CompanyResponse::fromEntity)
                .toList();
    }
}
