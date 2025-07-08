package com.kbe5.domain.manager.dto;


import com.kbe5.domain.manager.entity.Manager;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ManagerInfo {
    private Long id;
    private Long companyId;
    private String name;
    private String phone;
    private String email;
    private String loginId;
    private String companyCode;

    public static ManagerInfo fromEntity(Manager manager) {
        return ManagerInfo.builder()
                .id(manager.getId())
                .companyId(manager.getCompany().getId())
                .name(manager.getName())
                .phone(manager.getPhone())
                .email(manager.getEmail())
                .loginId(manager.getLoginId())
                .companyCode(manager.getCompanyCode())
                .build();
    }
}