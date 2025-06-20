package com.kbe5.rento.domain.manager.dto.request;

public record ManagerLoginRequest(
        String loginId,
        String password
) {
}
