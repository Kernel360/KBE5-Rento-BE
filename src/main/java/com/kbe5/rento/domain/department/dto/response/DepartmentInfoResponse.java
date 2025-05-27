package com.kbe5.rento.domain.department.dto.response;

public record DepartmentInfoResponse(
        Long departmentId,
        String departmentName,
        int numberOfEmployees
) {
}
