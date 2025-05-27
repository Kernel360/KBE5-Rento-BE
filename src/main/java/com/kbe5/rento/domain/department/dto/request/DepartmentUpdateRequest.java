package com.kbe5.rento.domain.department.dto.request;

import jakarta.validation.constraints.NotBlank;

public record DepartmentUpdateRequest(
        @NotBlank(message = "기업코드는 필수입니다.")
        String companyCode,
        @NotBlank(message = "부서이름은 필수입니다.")
        String departmentName
) {
}
