package com.kbe5.domain.company.dto;

import com.kbe5.domain.company.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CompanyInfo {

    private Long id;
    private String name;
    private int bizNumber;
    private String companyCode;

    public static CompanyInfo fromEntity(Company company) {
        return new CompanyInfo(
                company.getId(),
                company.getName(),
                company.getBizNumber(),
                company.getCompanyCode()
        );
    }
}