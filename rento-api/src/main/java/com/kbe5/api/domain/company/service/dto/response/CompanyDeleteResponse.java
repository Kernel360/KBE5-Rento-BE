package com.kbe5.api.domain.company.service.dto.response;

public record CompanyDeleteResponse(
        Long id,
        boolean isSuccess
) {
    public static CompanyDeleteResponse fromEntity(Long id, boolean result) {
       return new CompanyDeleteResponse(id, result);
    }
}
