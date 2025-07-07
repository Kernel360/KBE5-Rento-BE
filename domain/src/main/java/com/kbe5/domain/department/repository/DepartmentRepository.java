package com.kbe5.domain.department.repository;

import com.kbe5.domain.department.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByDepartmentNameAndCompanyId(String departmentName, Long companyId);
    List<Department> findAllByCompanyId(Long companyId);
}
