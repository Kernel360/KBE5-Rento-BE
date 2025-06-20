package com.kbe5.api.domain.manager.dto.response;

import com.kbe5.rento.domain.manager.entity.Manager;

public record ManagerSignUpResponse(
        Long id,
        String loginId,
        String companyCode
) {
    public static ManagerSignUpResponse fromEntity(Manager manager) {
        return new ManagerSignUpResponse(manager.getId(), manager.getLoginId(), manager.getCompanyCode());
    }
}
