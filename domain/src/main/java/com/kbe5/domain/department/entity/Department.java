package com.kbe5.domain.department.entity;


import com.kbe5.domain.BaseEntity;
import com.kbe5.domain.company.entity.Company;
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

    @Column(name = "number_of_employee", columnDefinition = "INT DEFAULT 0")
    private int numberOfEmployee;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Builder
    private Department(String departmentName, Company company, int numberOfEmployee) {
        this.company = company;
        this.departmentName = departmentName;
        this.numberOfEmployee = numberOfEmployee;
    }

    public void update(String departmentName) {
        this.departmentName = departmentName;
    }

}
