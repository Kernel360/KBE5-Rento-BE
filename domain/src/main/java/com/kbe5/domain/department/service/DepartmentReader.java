package com.kbe5.domain.department.service;

import com.kbe5.domain.department.entity.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentReader {
    Department getDepartmentById(Long departmentId);

    List<Department> getDepartmentsByCompanyId(Long companyId);

    Department findById(Long departmentId);

    boolean existsByDepartmentNameAndCompanyId(String departmentName, Long companyId);
}
