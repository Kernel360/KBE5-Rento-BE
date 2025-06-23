package com.kbe5.api.domain.department.vo;

import com.kbe5.api.domain.department.dto.request.DepartmentUpdateRequest;
import com.kbe5.domain.department.entity.Department;
import lombok.Getter;

@Getter
public class DepartmentUpdateVO {

    private final String departmentName;

    public DepartmentUpdateVO(DepartmentUpdateRequest request) {
        this.departmentName = request.departmentName();
    }

    public void update(Department department) {
        department.update(departmentName);
    }
}
