package com.kbe5.rento.domain.company.dto.response;

public record CompanyDeleteResponse(
        Long id,
        boolean isSuccess
) {
    public static CompanyDeleteResponse from(Long id, boolean result) {
       return new CompanyDeleteResponse(id, result);
    }
}
