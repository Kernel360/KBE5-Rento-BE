package com.kbe5.rento.domain.manager.dto.response;

import com.kbe5.rento.domain.manager.entity.Manager;

public record ManagerSignUpResponse(
        Long id,
        String loginId,
        String companyCode
) {
    public static ManagerSignUpResponse from(Manager manager, String companyCode) {
        return new ManagerSignUpResponse(manager.getId(), manager.getLoginId(), companyCode);
    }
}
