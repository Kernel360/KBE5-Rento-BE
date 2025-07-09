package com.kbe5.api.domain.company.service.mapper;

import com.kbe5.api.domain.company.service.dto.request.CompanyRegisterRequest;
import com.kbe5.domain.company.entity.Company;

public class CompanyMapper {

    public static Company toEntity(CompanyRegisterRequest request) {
        return new Company(request.bizNumber(), request.name());
    }
}
