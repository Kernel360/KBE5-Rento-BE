package com.kbe5.rento.domain.manager.dto.response;

import com.kbe5.rento.domain.manager.entity.Manager;

public record ManagerUpdateResponse(
        Long id,
        Long companyId,
        String name,
        String phone,
        String email,
        String loginId
) {
    public static ManagerUpdateResponse fromEntity(Manager manager) {
        return new ManagerUpdateResponse(manager.getId(), manager.getCompany().getId(), manager.getName(),
                manager.getPhone(), manager.getEmail(), manager.getLoginId());
    }
}
