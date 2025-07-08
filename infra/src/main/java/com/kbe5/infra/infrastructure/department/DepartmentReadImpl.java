package com.kbe5.infra.infrastructure.department;

import com.kbe5.domain.department.entity.Department;
import com.kbe5.domain.department.service.DepartmentReader;
import com.kbe5.domain.exception.DomainException;
import com.kbe5.domain.exception.ErrorType;
import com.kbe5.infra.infrastructure.department.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DepartmentReadImpl implements DepartmentReader {

    private final DepartmentRepository departmentRepository;

    @Override
    public List<Department> getDepartmentsByCompanyId(Long companyId) {
        return departmentRepository.findAllByCompanyId(companyId);
    }

    @Override
    public Department findById(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new DomainException(ErrorType.DEPARTMENT_NOT_FOUND));
    }

    @Override
    public boolean existsByDepartmentNameAndCompanyId(String departmentName, Long companyId) {
        return departmentRepository.existsByDepartmentNameAndCompanyId(departmentName, companyId);
    }

    @Override
    public Department getDepartmentById(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(()->new DomainException(ErrorType.DEPARTMENT_NOT_FOUND));
    }
}
