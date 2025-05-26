package com.kbe5.rento.domain.department.service;

import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.common.exception.ErrorType;
import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.company.repository.CompanyRepository;
import com.kbe5.rento.domain.department.dto.request.DepartmentRegisterRequest;
import com.kbe5.rento.domain.department.dto.request.DepartmentUpdateRequest;
import com.kbe5.rento.domain.department.dto.response.DepartmentInfoResponse;
import com.kbe5.rento.domain.department.entity.Department;
import com.kbe5.rento.domain.department.repository.DepartmentRepository;
import com.kbe5.rento.domain.member.entity.Member;
import com.kbe5.rento.domain.member.repository.MemberRepository;
import lombok.AccessLevel;
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
    public void register(DepartmentRegisterRequest departmentRegisterRequest) {
        Company company = companyRepository.findByCompanyCode(departmentRegisterRequest.companyCode())
                .orElseThrow(() -> new DomainException(ErrorType.NO_SEARCH_RESULTS));

        validateDuplicateDepartmentName(departmentRegisterRequest.departmentName(), company.getId());

        Department department = DepartmentRegisterRequest.toEntity(departmentRegisterRequest, company);

        departmentRepository.save(department);
    }

    @Transactional(readOnly = true)
    public List<DepartmentInfoResponse> getDepartments(String companyCode) {
        Company company = companyRepository.findByCompanyCode(companyCode).orElseThrow(
                () -> new DomainException(ErrorType.NO_SEARCH_RESULTS)
        );

       return departmentRepository.findAllByCompanyId(company.getId()).stream()
               .map(this::convertToDepartmentDto)
               .toList();
    }

    @Transactional
    public DepartmentInfoResponse updateDepartment(Long departmentId, DepartmentUpdateRequest departmentUpdateRequest) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new DomainException(ErrorType.NO_SEARCH_RESULTS));

        Company company = companyRepository.findByCompanyCode(departmentUpdateRequest.companyCode())
                .orElseThrow(() -> new DomainException(ErrorType.NO_SEARCH_RESULTS));

        validateDuplicateDepartmentName(departmentUpdateRequest.departmentName(), company.getId());

        department.update(departmentUpdateRequest);

        return convertToDepartmentDto(department);
    }

    @Transactional
    public void delete(Long departmentId) {
        List<Member> members = memberRepository.findAllByDepartmentId(departmentId);

        if(!members.isEmpty()) {
            throw new IllegalArgumentException("해당 부서에 소속된 직원이 있어 삭제할 수 없습니다.");
        }

        Department department = departmentRepository.findById(departmentId).orElseThrow(
                () -> new DomainException(ErrorType.NO_SEARCH_RESULTS)
        );

        departmentRepository.delete(department);
    }

    private void validateDuplicateDepartmentName(String departmentName, Long companyId) {
        if(departmentRepository.existsByDepartmentNameAndCompanyId(departmentName, companyId)) {
            throw new IllegalArgumentException("이미 존재하는 부서 이름입니다.");
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
