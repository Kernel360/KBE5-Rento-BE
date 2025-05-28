package com.kbe5.rento.domain.manager.dto.response;

import com.kbe5.rento.domain.manager.entity.Manager;

public record ManagerLoginResponse(
        String loginId,
        String name,
        String phone,
        String email,
        String companyCode,
        long companyId
) {
    public static ManagerLoginResponse fromEntity(Manager manager) {
        return new ManagerLoginResponse(manager.getLoginId(), manager.getName(), manager.getPhone(),
                manager.getEmail(), manager.getCompanyCode(), manager.getCompany().getId());
    }
}
