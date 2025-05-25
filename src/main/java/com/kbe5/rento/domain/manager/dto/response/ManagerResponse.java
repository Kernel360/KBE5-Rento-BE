package com.kbe5.rento.domain.manager.dto.response;

import com.kbe5.rento.domain.manager.entity.Manager;

import java.util.List;

public record ManagerResponse(
        Long id,
        Long companyId,
        String name,
        String phone,
        String email,
        String loginId
) {
    public static ManagerResponse from(Manager manager) {
        return new ManagerResponse(manager.getId(), manager.getCompanyId().getId(), manager.getName(),
                manager.getPhone(), manager.getEmail(), manager.getLoginId());
    }

    public static List<ManagerResponse> from(List<Manager> managerList) {
        return managerList.stream()
                .map(ManagerResponse::from)
                .toList();
    }
}
