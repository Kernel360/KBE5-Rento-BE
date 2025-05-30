package com.kbe5.rento.domain.member.entity;

import com.kbe5.rento.common.util.BaseEntity;
import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.department.entity.Department;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Table(name="members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    private Position position;

    @Column(name = "login_id")
    private String loginId;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "company_code")
    private String companyCode;

    @JoinColumn(name = "department_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    @JoinColumn(name = "company_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    @Builder
    private Member(String name, String email, Position position, String loginId,
                  String password, String phoneNumber, String companyCode) {
        this.name = name;
        this.email = email;
        this.position = position;
        this.loginId = loginId;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.companyCode = companyCode;
    }

    public void update(String name, String email, Position position, String loginId, String phoneNumber, Department department) {
        this.name = name;
        this.email = email;
        this.position = position;
        this.loginId = loginId;
        this.phoneNumber = phoneNumber;
        this.department = department;
    }

    public String getPosition(){
        return this.position.getDisplayName();
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    public void assignCompany(Company company) {
        this.company = company;
    }

    public void assignDepartment(Department department) {
        this.department = department;
    }
}
