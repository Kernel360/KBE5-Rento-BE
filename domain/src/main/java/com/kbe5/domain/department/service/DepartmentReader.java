package com.kbe5.domain.department.service;

import com.kbe5.domain.department.entity.Department;

import java.util.List;

public interface DepartmentReader {
    Department getDepartmentById(Long departmentId);

    List<Department> getDepartmentsByCompanyId(Long companyId);
}
