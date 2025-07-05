package com.kbe5.api.domain.manager.dto.response;

public record ManagerSignUpResponse(
        Long id,
        String loginId,
        String companyCode
) {
}
