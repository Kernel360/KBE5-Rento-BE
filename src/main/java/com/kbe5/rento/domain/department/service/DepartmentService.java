package com.kbe5.rento.domain.department.service;

import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.common.exception.ErrorType;
import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.company.repository.CompanyRepository;
import com.kbe5.rento.domain.department.dto.request.DepartmentUpdateRequest;
import com.kbe5.rento.domain.department.dto.response.DepartmentInfoResponse;
import com.kbe5.rento.domain.department.entity.Department;
import com.kbe5.rento.domain.department.repository.DepartmentRepository;
import com.kbe5.rento.domain.manager.entity.Manager;
import com.kbe5.rento.domain.member.entity.Member;
import com.kbe5.rento.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final CompanyRepository companyRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Department register(Department department) {
        Company company = companyRepository.findById(department.getCompany().getId())
                .orElseThrow(() -> new DomainException(ErrorType.COMPANY_NOT_FOUND));

        validateDuplicateDepartmentName(department.getDepartmentName(), company.getId());

        return departmentRepository.save(department);
    }

    @Transactional(readOnly = true)
    public List<Department> getDepartments(String companyCode) {
        Company company = companyRepository.findByCompanyCode(companyCode).orElseThrow(
                () -> new DomainException(ErrorType.COMPANY_NOT_FOUND)
        );

       return departmentRepository.findAllByCompanyId(company.getId()).stream()
               .toList();
    }

    @Transactional
    public DepartmentInfoResponse updateDepartment(Manager manager, Long departmentId, DepartmentUpdateRequest departmentUpdateRequest) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new DomainException(ErrorType.DEPARTMENT_NOT_FOUND));

        Company company = companyRepository.findByCompanyCode(departmentUpdateRequest.companyCode())
                .orElseThrow(() -> new DomainException(ErrorType.COMPANY_NOT_FOUND));

        if (!manager.getCompany().getId().equals(company.getId())) {
            throw new DomainException(ErrorType.NOT_AUTHORIZED);
        }

        validateDuplicateDepartmentName(departmentUpdateRequest.departmentName(), company.getId());

        department.update(departmentUpdateRequest);

        return convertToDepartmentDto(department);
    }

    @Transactional
    public void delete(Manager manager, Long departmentId) {
        List<Member> members = memberRepository.findAllByDepartmentId(departmentId);

        if(!members.isEmpty()) {
            throw new DomainException(ErrorType.ALREADY_MEMBER);
        }

        Department department = departmentRepository.findById(departmentId).orElseThrow(
                () -> new DomainException(ErrorType.DEPARTMENT_NOT_FOUND)
        );

        if (!manager.getCompany().getId().equals(department.getCompany().getId())) {
            throw new DomainException(ErrorType.NOT_AUTHORIZED);
        }

        departmentRepository.delete(department);
    }

    private void validateDuplicateDepartmentName(String departmentName, Long companyId) {
        if(departmentRepository.existsByDepartmentNameAndCompanyId(departmentName, companyId)) {
            throw new DomainException(ErrorType.DUPLICATE_DEPARTMENT_NAME);
        }
    }

    private DepartmentInfoResponse convertToDepartmentDto(Department department) {
        List<Member> members = memberRepository.findAllByDepartmentId(department.getId())
                .stream()
                .toList();

        return new DepartmentInfoResponse(
                department.getId(),
                department.getDepartmentName(),
                members.size()
        );
    }

}
