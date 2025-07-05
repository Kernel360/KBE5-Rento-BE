package com.kbe5.domain.department.dto;

import com.kbe5.domain.department.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class DepartmentInfo {
    private final Long departmentId;
    private final String departmentName;
    private final int numberOfEmployees;

    public static DepartmentInfo fromEntity(Department department) {
        return DepartmentInfo.builder()
                .departmentId(department.getId())
                .departmentName(department.getDepartmentName())
                .numberOfEmployees(department.getNumberOfEmployee())
                .build();
    }
}
