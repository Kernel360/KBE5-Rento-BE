package com.kbe5.rento.domain.manager.dto.request;

import com.kbe5.rento.domain.company.entity.Company;

public record ManagerSignUpRequest(
        String managerId,
        String password,
        String name,
        String phone,
        String email,
        String companyCode
) {
}
