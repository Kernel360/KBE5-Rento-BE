package com.kbe5.rento.domain.department.repository;

import com.kbe5.rento.domain.department.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findAllByCompanyId(Long id);

    boolean existsByDepartmentNameAndCompanyId(String departmentName, Long companyId);

}
