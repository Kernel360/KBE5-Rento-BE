package com.kbe5.api.domain.department.dto.request;

import com.kbe5.domain.company.entity.Company;
import com.kbe5.domain.department.entity.Department;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DepartmentRegisterRequest(
        @NotBlank(message = "기업코드는 필수입니다.")
        String companyCode,
        @NotBlank(message = "부서이름은 필수입니다.")
        @Size(max = 10, message = "부서이름은 10자 이내여야 합니다.")
        String departmentName
) {
        public static Department of(DepartmentRegisterRequest departmentRegisterRequest, Company company) {
                return Department.builder()
                        .departmentName(departmentRegisterRequest.departmentName())
                        .company(company)
                        .build();
        }
}