package com.kbe5.api.domain.department.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DepartmentUpdateRequest(
        @NotNull(message = "회사Id는 필수입니다.")
        Long companyId,
        @NotBlank(message = "부서이름은 필수입니다.")
        @Size(max = 10, message = "부서이름은 10자 이내여야 합니다.")
        String departmentName
) {
}
