package com.kbe5.api.domain.department.dto.response;


import com.kbe5.domain.department.dto.DepartmentInfo;
import com.kbe5.domain.department.entity.Department;

public record DepartmentInfoResponse(
        Long departmentId,
        String departmentName,
        int numberOfEmployees
) {
    public static DepartmentInfoResponse fromEntity(Department department) {
        return new DepartmentInfoResponse(
                department.getId(),
                department.getDepartmentName(),
                department.getNumberOfEmployee()
        );
    }

    public static DepartmentInfoResponse fromDepartmentInfo(DepartmentInfo departmentInfo) {
        return new DepartmentInfoResponse(
                departmentInfo.getDepartmentId(),
                departmentInfo.getDepartmentName(),
                departmentInfo.getNumberOfEmployees()
        );
    }
}
