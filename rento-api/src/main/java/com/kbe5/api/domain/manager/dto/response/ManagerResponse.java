package com.kbe5.api.domain.manager.dto.response;

public record ManagerResponse(
        Long id,
        Long companyId,
        String name,
        String phone,
        String email,
        String loginId
) {
}
