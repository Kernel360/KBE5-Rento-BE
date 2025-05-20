package com.kbe5.rento.domain.manager.dto.request;

public record ManagerDeleteRequest(
        Long id,
        String managerId,
        String password
) {
}
