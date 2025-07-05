package com.kbe5.domain.department.service;

import com.kbe5.domain.department.dto.DepartmentCommand;
import com.kbe5.domain.department.entity.Department;

public interface DepartmentStore {
    Department store(Department initDepartment);
    void delete(Department department);
    void update(DepartmentCommand.Update department, Long departmentId);
}
