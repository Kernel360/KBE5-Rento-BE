package com.kbe5.infra.infrastructure.department;

import com.kbe5.domain.company.entity.Company;
import com.kbe5.domain.company.service.CompanyReader;
import com.kbe5.domain.department.dto.DepartmentCommand;
import com.kbe5.domain.department.entity.Department;
import com.kbe5.domain.department.service.DepartmentReader;
import com.kbe5.domain.department.service.DepartmentStore;
import com.kbe5.domain.exception.DomainException;
import com.kbe5.domain.exception.ErrorType;
import com.kbe5.infra.infrastructure.department.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DepartmentStoreImpl implements DepartmentStore {

    private final DepartmentRepository departmentRepository;
    private final CompanyReader companyReader;
    private final DepartmentReader departmentReader;

    @Override
    public Department store(Department department) {
        Company company = companyReader.findById(department.getCompanyId());
        validateDuplicateDepartmentName(department.getDepartmentName(), company.getId());

        return departmentRepository.save(department);
    }

    public void update(DepartmentCommand.Update departmentUpdate, Long departmentId) {
        Company company = companyReader.findByCompanyCode(departmentUpdate.getCompanyCode());
        validateDuplicateDepartmentName(departmentUpdate.getDepartmentName(), company.getId());

        Department existingDepartment = departmentReader.findById(departmentId);

        existingDepartment.update(departmentUpdate.getDepartmentName());
    }

    @Override
    public void delete(Department department) {
        departmentRepository.delete(department);
    }

    private void validateDuplicateDepartmentName(String departmentName, Long companyId) {
        if(departmentReader.existsByDepartmentNameAndCompanyId(departmentName, companyId)) {
            throw new DomainException(ErrorType.DUPLICATE_DEPARTMENT_NAME);
        }
    }
}

