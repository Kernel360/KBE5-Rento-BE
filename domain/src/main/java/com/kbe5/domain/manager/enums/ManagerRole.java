package com.kbe5.domain.manager.enums;

import lombok.Getter;

@Getter
public enum ManagerRole {
    ROLE_ADMIN("ROLE_ADMIN"),   // 최고 관리자
    ROLE_MANAGER("ROLE_MANAGER"); // 일반 관리자

    private final String value;

    ManagerRole(String value) {
        this.value = value;
    }
}
