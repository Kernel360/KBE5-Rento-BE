package com.kbe5.rento.domain.manager.entity;

import com.kbe5.rento.common.util.BaseEntity;
import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.manager.dto.request.ManagerUpdateRequest;
import com.kbe5.rento.domain.manager.enums.ManagerRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private ManagerRole role;

    private String fcmToken;

    @Builder
    private Manager(Company company, String name, String phone, String email, String loginId, String password,
                    String companyCode, ManagerRole role, String fcmToken) {
        this.company = company;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.loginId = loginId;
        this.password = password;
        this.companyCode = companyCode;
        this.role = role;
        this.fcmToken = fcmToken;
    }

    public void toUpdate(ManagerUpdateRequest request) {
        this.name = request.name();
        this.phone = request.phone();
        this.email = request.email();
    }

    public void encodePassword(PasswordEncoder encoder) {
        this.password = encoder.encode(password);
    }

    public void assignCompany(Company company) {
        this.company = company;
    }

    public void assignRole(ManagerRole role) {
        this.role = role;
    }

    public void assignFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
