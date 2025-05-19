package com.kbe5.rento.domain.company.dto.response;

import com.kbe5.rento.domain.company.entity.Company;

import java.util.List;

public record CompanyListResponse(
        List<Company> companies
) {
}
