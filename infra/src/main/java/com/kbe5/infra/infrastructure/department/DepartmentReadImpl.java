package com.kbe5.infra.infrastructure.department;

import com.kbe5.domain.department.entity.Department;
import com.kbe5.domain.department.repository.DepartmentRepository;
import com.kbe5.domain.department.service.DepartmentReader;
import com.kbe5.domain.exception.DomainException;
import com.kbe5.domain.exception.ErrorType;
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
    public Department getDepartmentById(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(()->new DomainException(ErrorType.DEPARTMENT_NOT_FOUND));
    }
}
