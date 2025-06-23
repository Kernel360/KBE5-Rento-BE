package com.kbe5.api.domain.manager.vo;

import com.kbe5.api.domain.manager.dto.request.ManagerUpdateRequest;
import com.kbe5.domain.manager.entity.Manager;
import lombok.Getter;

@Getter
public class ManagerUpdateVO {

    private final String name;

    private final String phone;

    private final String email;

    public ManagerUpdateVO(ManagerUpdateRequest request) {
        this.name = request.name();
        this.phone = request.phone();
        this.email = request.email();
    }

    public void update(Manager manager) {
        manager.toUpdate(name, phone, email);
    }
}
