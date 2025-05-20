package com.kbe5.rento.domain.manager.dto.response;

import com.kbe5.rento.domain.company.entity.Company;

public record ManagerDetailResponse(
        Long id,
        Company companyId,
        String name,
        String phone,
        String email,
        String managerId
) {
}
