package com.kbe5.api.domain.department.mapper;

import com.kbe5.api.domain.department.dto.request.DepartmentRegisterRequest;
import com.kbe5.api.domain.department.dto.request.DepartmentUpdateRequest;
import com.kbe5.domain.department.dto.DepartmentCommand;
import org.springframework.stereotype.Component;

@Component
public class DepartmentRequestMapper {

    public DepartmentCommand.Register toRegisterCommand(DepartmentRegisterRequest request, Long companyId) {
        return DepartmentCommand.Register.builder()
                .departmentName(request.departmentName())
                .companyId(companyId)
                .build();
    }

    public DepartmentCommand.Update toUpdateCommand(DepartmentUpdateRequest request, Long companyId) {
        return DepartmentCommand.Update.builder()
                .departmentName(request.departmentName())
                .companyId(companyId)
                .build();
    }
}