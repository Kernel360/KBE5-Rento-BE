package com.kbe5.rento.domain.manager.dto.response;

public record ManagerSignUpResponse(
        Long id,
        String managerId,
        String companyCode
) {
}
