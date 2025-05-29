package com.kbe5.rento.domain.member.service;

import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.common.exception.ErrorType;
import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.company.repository.CompanyRepository;
import com.kbe5.rento.domain.department.entity.Department;
import com.kbe5.rento.domain.department.repository.DepartmentRepository;
import com.kbe5.rento.domain.manager.entity.Manager;
import com.kbe5.rento.domain.member.dto.request.MemberUpdateRequest;
import com.kbe5.rento.domain.member.dto.response.MemberInfoResponse;
import com.kbe5.rento.domain.member.entity.Member;
import com.kbe5.rento.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final CompanyRepository companyRepository;

    @Transactional
    public Member register(Member member, Long departmentId) {
        validateDuplicatePhoneNumber(member.getPhoneNumber(), member.getCompanyCode());

        member.encodePassword(passwordEncoder);

        member.assignCompany(companyRepository.findByCompanyCode(member.getCompanyCode()).orElseThrow(
                () -> new DomainException(ErrorType.COMPANY_NOT_FOUND))
        );
        member.assignDepartment(departmentRepository.findById(departmentId).orElseThrow(
                () -> new DomainException(ErrorType.DEPARTMENT_NOT_FOUND))
        );

        return memberRepository.save(member);
    }

    @Transactional
    public MemberInfoResponse update(Manager manager, MemberUpdateRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new DomainException(ErrorType.MEMBER_NOT_FOUND));

        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new DomainException(ErrorType.DEPARTMENT_NOT_FOUND));

        if(!manager.getCompany().getId().equals(member.getCompany().getId())) {
            throw new DomainException(ErrorType.NOT_AUTHORIZED);
        }

        validateDuplicatePhoneNumber(request.phoneNumber(), request.companyCode());

        member.update(request.name(), request.email(), request.getPosition(), request.loginId(), request.phoneNumber(), department);

        return MemberInfoResponse.from(member);
    }

    @Transactional
    public void delete(Manager manager, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new DomainException(ErrorType.MEMBER_NOT_FOUND));

        if(!manager.getCompany().getId().equals(member.getCompany().getId())) {
            throw new DomainException(ErrorType.NOT_AUTHORIZED);
        }

        memberRepository.delete(member);
    }

    @Transactional(readOnly = true)
    public List<Member> getMemberList(String companyCode) {
        Company company = companyRepository.findByCompanyCode(companyCode)
                .orElseThrow(() -> new DomainException(ErrorType.COMPANY_NOT_FOUND));

        return memberRepository.findAllByCompanyId(company.getId());

    }

    @Transactional(readOnly = true)
    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new DomainException(ErrorType.MEMBER_NOT_FOUND));
    }

    private void validateDuplicatePhoneNumber(String phoneNumber, String companyCode) {
        Company company = companyRepository.findByCompanyCode(companyCode).orElseThrow(
                () -> new DomainException(ErrorType.COMPANY_NOT_FOUND)
        );

        if(memberRepository.existsByPhoneNumberAndCompanyId(phoneNumber, company.getId())) {
            throw new DomainException(ErrorType.DUPLICATE_PHONE_NUMBER);
        }
    }

}
