package com.kbe5.rento.domain.manager.dto.response;

public record ManagerDeleteResponse(
        boolean isSuccess
) {
    public static ManagerDeleteResponse fromEntity(boolean result) {
        return new ManagerDeleteResponse(result);
    }
}
