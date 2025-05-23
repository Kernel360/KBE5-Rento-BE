package com.kbe5.rento.domain.company.dto.response;

public record CompanyDeleteResponse(
        Long id,
        Boolean isSuccess
) {
    public static CompanyDeleteResponse from(Long id, boolean result) {
       return new CompanyDeleteResponse(id, result);
    }
}
