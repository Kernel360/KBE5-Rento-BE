package com.kbe5.api.domain.manager.dto.request;

public record ManagerLoginRequest(
        String loginId,
        String password
) {
}
