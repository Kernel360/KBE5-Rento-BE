package com.kbe5.domain.department.service;

import com.kbe5.domain.department.dto.DepartmentCommand;
import com.kbe5.domain.department.dto.DepartmentInfo;

import java.util.List;

public interface DepartmentService {
    DepartmentInfo registerDepartment(DepartmentCommand.Register command);
    List<DepartmentInfo> getDepartments(Long companyId);
    DepartmentInfo updateDepartment(Long departmentId, DepartmentCommand.Update departmentUpdateRequest);
    void delete(Long departmentId);
}
