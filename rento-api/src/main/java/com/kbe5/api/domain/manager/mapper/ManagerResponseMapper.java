package com.kbe5.api.domain.manager.mapper;

import com.kbe5.api.domain.manager.dto.response.ManagerDeleteResponse;
import com.kbe5.api.domain.manager.dto.response.ManagerResponse;
import com.kbe5.api.domain.manager.dto.response.ManagerSignUpResponse;
import com.kbe5.api.domain.manager.dto.response.ManagerUpdateResponse;
import com.kbe5.domain.manager.dto.ManagerInfo;

import java.util.List;

public class ManagerResponseMapper {

    public static ManagerSignUpResponse toSignUpResponse(ManagerInfo info) {
        return new ManagerSignUpResponse(
                info.getId(),
                info.getLoginId(),
                info.getCompanyCode()
        );
    }

    public static ManagerUpdateResponse toUpdateResponse(ManagerInfo info) {
        return new ManagerUpdateResponse(
                info.getCompanyId(),
                info.getName(),
                info.getPhone(),
                info.getEmail(),
                info.getLoginId()
        );
    }

    public static ManagerResponse toResponse(ManagerInfo info) {
        return new ManagerResponse(
                info.getId(),
                info.getCompanyId(),
                info.getName(),
                info.getPhone(),
                info.getEmail(),
                info.getLoginId()
        );
    }

    public static List<ManagerResponse> toResponseList(List<ManagerInfo> infoList) {
        return infoList.stream()
                .map(ManagerResponseMapper::toResponse)
                .toList();
    }

    public static ManagerDeleteResponse toDeleteResponse(boolean result) {
        return new ManagerDeleteResponse(result);
    }
}