package com.kbe5.domain.department.entity;


import com.kbe5.domain.BaseEntity;
import com.kbe5.domain.company.entity.Company;
import com.kbe5.domain.department.dto.DepartmentInfo;
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

    private Long companyId;

    @Builder
    public Department(String departmentName, int numberOfEmployee, Long companyId) {
        this.departmentName = departmentName;
        this.numberOfEmployee = numberOfEmployee;
        this.companyId = companyId;
    }

    public DepartmentInfo toDepartmentInfo(int numberOfEmployees) {
        return DepartmentInfo.builder()
                .departmentId(this.getId())
                .departmentName(this.getDepartmentName())
                .numberOfEmployees(numberOfEmployees)
                .build();
    }

    public void update(String departmentName) {
        this.departmentName = departmentName;
    }
}
