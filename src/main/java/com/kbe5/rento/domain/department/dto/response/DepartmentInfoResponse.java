package com.kbe5.rento.domain.department.dto.response;

import com.kbe5.rento.domain.department.entity.Department;

public record DepartmentInfoResponse(
        Long departmentId,
        String departmentName,
        int numberOfEmployees
) {
    public static   DepartmentInfoResponse fromEntity(Department department) {
        return new DepartmentInfoResponse(
                department.getId(),
                department.getDepartmentName(),
                department.getNumberOfEmployee()
        );
    }
}
