package com.kbe5.api.domain.company.mapper;

import com.kbe5.api.domain.company.dto.request.companyBizNumberRequest;
import com.kbe5.api.domain.company.dto.request.CompanyRegisterRequest;
import com.kbe5.api.domain.company.dto.request.CompanyUpdateRequest;
import com.kbe5.domain.company.dto.CompanyCommand;

public class CompanyRequestMapper {

    public static CompanyCommand.Register toCommand(CompanyRegisterRequest request) {
        return CompanyCommand.Register.builder()
                .name(request.name())
                .bizNumber(request.bizNumber())
                .build();
    }

    public static CompanyCommand.Update toCommand(CompanyUpdateRequest request) {
        return CompanyCommand.Update.builder()
                .name(request.name())
                .bizNumber(request.bizNumber())
                .build();
    }

    public static CompanyCommand.CheckBizNumber toCommand(companyBizNumberRequest request) {
        return CompanyCommand.CheckBizNumber.builder()
                .bizNumber(request.bizNumber())
                .build();
    }
}
