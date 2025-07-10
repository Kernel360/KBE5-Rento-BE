package com.kbe5.api.domain.department.dto.response;

public record DepartmentInfoResponse(
        Long departmentId,
        String departmentName,
        int numberOfEmployees
) {
}
