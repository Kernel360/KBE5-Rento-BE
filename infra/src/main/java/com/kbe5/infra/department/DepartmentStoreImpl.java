package com.kbe5.infra.department;

import com.kbe5.domain.company.entity.Company;
import com.kbe5.domain.company.repository.CompanyRepository;
import com.kbe5.domain.department.dto.DepartmentCommand;
import com.kbe5.domain.department.entity.Department;
import com.kbe5.domain.department.repository.DepartmentRepository;
import com.kbe5.domain.department.service.DepartmentStore;
import com.kbe5.domain.exception.DomainException;
import com.kbe5.domain.exception.ErrorType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DepartmentStoreImpl implements DepartmentStore {

    private final DepartmentRepository departmentRepository;
    private final CompanyRepository companyRepository;

    @Override
    public Department store(Department department) {
        Company company = companyRepository.findById(department.getCompanyId())
                .orElseThrow(() -> new DomainException(ErrorType.COMPANY_NOT_FOUND));

        validateDuplicateDepartmentName(department.getDepartmentName(), company.getId());

        return departmentRepository.save(department);
    }

    public void update(DepartmentCommand.Update departmentUpdate, Long departmentId) {
        Company company = companyRepository.findById(departmentUpdate.getCompanyId())
                .orElseThrow(() -> new DomainException(ErrorType.COMPANY_NOT_FOUND));

        validateDuplicateDepartmentName(departmentUpdate.getDepartmentName(), company.getId());

        Department existingDepartment = departmentRepository.findById(departmentId)
                        .orElseThrow(() -> new DomainException(ErrorType.DEPARTMENT_NOT_FOUND));

        existingDepartment.update(departmentUpdate.getDepartmentName());
    }

    @Override
    public void delete(Department department) {
        departmentRepository.delete(department);
    }

    private void validateDuplicateDepartmentName(String departmentName, Long companyId) {
        if(departmentRepository.existsByDepartmentNameAndCompanyId(departmentName, companyId)) {
            throw new DomainException(ErrorType.DUPLICATE_DEPARTMENT_NAME);
        }
    }
}

