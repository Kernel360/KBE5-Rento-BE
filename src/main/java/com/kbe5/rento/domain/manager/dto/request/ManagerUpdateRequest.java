package com.kbe5.rento.domain.manager.dto.request;

import com.kbe5.rento.domain.company.entity.Company;

public record ManagerUpdateRequest(
        Long id,
        String name,
        String phone,
        String email
) {
}
