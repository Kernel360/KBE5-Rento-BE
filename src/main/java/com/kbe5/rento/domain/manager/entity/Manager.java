package com.kbe5.rento.domain.manager.entity;

import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.common.util.BaseEntity;
import com.kbe5.rento.domain.manager.dto.request.ManagerUpdateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Manager extends BaseEntity {

    @ManyToOne
    private Company companyId;

    @Column(length = 30)
    private String name;

    @Column(length = 30)
    private String phone;

    @Column(length = 30)
    private String email;

    @Column(length = 30)
    private String managerId;

    @Column(length = 30)
    private String password;

    @Builder
    private Manager(Company companyId, String name, String phone, String email, String managerId, String password) {
        this.companyId = companyId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.managerId = managerId;
        this.password = password;
    }

    public Manager toUpdate(ManagerUpdateRequest request) {
        this.name = request.name();
        this.phone = request.phone();
        this.email = request.email();

        return this;
    }
}
