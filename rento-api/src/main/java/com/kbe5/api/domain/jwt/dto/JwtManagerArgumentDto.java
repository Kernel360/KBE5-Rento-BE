package com.kbe5.api.domain.jwt.dto;


import com.kbe5.api.domain.jwt.util.JwtUtil;
import com.kbe5.domain.manager.entity.Manager;

public record JwtManagerArgumentDto(
        String uuid,
        String loginId,
        Long companyId,
        String name,
        String email,
        String companyCode,
        String role
) {
    public static JwtManagerArgumentDto fromEntity(Manager manager) {
        return new JwtManagerArgumentDto(manager.getUuid(), manager.getLoginId(), manager.getCompany().getId(),
                manager.getName(), manager.getEmail(), manager.getCompanyCode(),
                manager.getRole().getValue());
    }

    public static JwtManagerArgumentDto of(JwtUtil util, String token) {
        return new JwtManagerArgumentDto(util.getUuid(token), util.getLoginId(token), util.getCompanyId(token),
                util.getName(token), util.getEmail(token), util.getCompanyCode(token), util.getRole(token));
    }
}
