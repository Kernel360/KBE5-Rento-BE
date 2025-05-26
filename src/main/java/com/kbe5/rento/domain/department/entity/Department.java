package com.kbe5.rento.domain.department.entity;

import com.kbe5.rento.common.util.BaseEntity;
import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.department.dto.request.DepartmentUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "departments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Department extends BaseEntity {
    @Column(name = "department_name")
    private String departmentName;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Builder
    public Department(String departmentName, Company company) {
        this.company = company;
        this.departmentName = departmentName;
    }

    public void update(DepartmentUpdateRequest departmentUpdateRequest) {
        this.departmentName = departmentUpdateRequest.departmentName();
    }

}
