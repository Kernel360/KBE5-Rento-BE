package com.kbe5.api.jwt.dto;

import com.kbe5.rento.common.jwt.util.JwtUtil;
import com.kbe5.rento.domain.manager.entity.Manager;

public record JwtManagerArgumentDto(
        Long id,
        String loginId,
        Long companyId,
        String name,
        String email,
        String companyCode,
        String role
) {
    public static JwtManagerArgumentDto fromEntity(Manager manager) {
        return new JwtManagerArgumentDto(manager.getId(), manager.getLoginId(), manager.getCompany().getId(),
                manager.getName(), manager.getEmail(), manager.getCompanyCode(),
                manager.getRole().getValue());
    }

    public static JwtManagerArgumentDto of(JwtUtil util, String token) {
        return new JwtManagerArgumentDto(util.getId(token), util.getLoginId(token), util.getCompanyId(token),
                util.getName(token), util.getEmail(token), util.getCompanyCode(token), util.getRole(token));
    }
}
