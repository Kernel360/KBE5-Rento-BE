package com.kbe5.rento.domain.manager.entity;

import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.common.util.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import com.kbe5.rento.domain.manager.dto.request.ManagerUpdateRequest;
import com.kbe5.rento.domain.manager.enums.ManagerRole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "managers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Manager extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(length = 30)
    private String name;

    @Column(length = 30)
    private String phone;

    @Column(length = 30)
    private String email;

    @Column(length = 30)
    private String loginId;

    private String password;

    @Column(length = 30)
    private String companyCode;

    @Column(length = 10)
    private ManagerRole role;

    @Builder
    private Manager(Company company, String name, String phone, String email, String loginId, String password,
                    String companyCode, ManagerRole role) {
        this.company = company;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.loginId = loginId;
        this.password = password;
        this.companyCode = companyCode;
        this.role = role;
    }

    public void toUpdate(ManagerUpdateRequest request) {
        this.name = request.name();
        this.phone = request.phone();
        this.email = request.email();
    }
}
