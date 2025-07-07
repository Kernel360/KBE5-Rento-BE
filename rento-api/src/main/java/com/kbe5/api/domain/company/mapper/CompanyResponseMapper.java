package com.kbe5.api.domain.company.mapper;

import com.kbe5.api.domain.company.dto.response.CompanyRegisterResponse;
import com.kbe5.api.domain.company.dto.response.CompanyResponse;
import com.kbe5.api.domain.company.dto.response.CompanyUpdateResponse;
import com.kbe5.domain.company.dto.CompanyInfo;

import java.util.List;

public class CompanyResponseMapper {

    public static CompanyRegisterResponse toRegisterResponse(CompanyInfo info) {
        return new CompanyRegisterResponse(
                info.getName(),
                info.getCompanyCode()
        );
    }

    public static CompanyResponse toResponse(CompanyInfo info) {
        return new CompanyResponse(
                info.getId(),
                info.getName(),
                info.getBizNumber(),
                info.getCompanyCode()
        );
    }

    public static List<CompanyResponse> toResponseList(List<CompanyInfo> infoList) {
        return infoList.stream()
                .map(CompanyResponseMapper::toResponse)
                .toList();
    }

    public static CompanyUpdateResponse toUpdateResponse(CompanyInfo info) {
        return new CompanyUpdateResponse(
                info.getBizNumber(),
                info.getName()
        );
    }
}
