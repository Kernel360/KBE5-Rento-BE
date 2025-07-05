package com.kbe5.api.domain.department.dto.request;

import com.kbe5.domain.department.dto.DepartmentCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DepartmentRegisterRequest(
        @NotBlank(message = "부서이름은 필수입니다.")
        @Size(max = 10, message = "부서이름은 10자 이내여야 합니다.")
        String departmentName
) {

        public DepartmentCommand.Register toCommand(Long companyId){
                return DepartmentCommand.Register.builder()
                        .departmentName(departmentName)
                        .companyId(companyId)
                        .build();
        }
}