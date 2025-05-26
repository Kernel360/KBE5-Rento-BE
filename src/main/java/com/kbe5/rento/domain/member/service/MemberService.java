package com.kbe5.rento.domain.member.service;

import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.company.repository.CompanyRepository;
import com.kbe5.rento.domain.department.entity.Department;
import com.kbe5.rento.domain.department.repository.DepartmentRepository;
import com.kbe5.rento.domain.member.dto.request.MemberRegisterRequest;
import com.kbe5.rento.domain.member.dto.request.MemberUpdateRequest;
import com.kbe5.rento.domain.member.dto.response.MemberInfoResponse;
import com.kbe5.rento.domain.member.entity.Member;
import com.kbe5.rento.domain.member.repository.MemberRepository;
import com.kbe5.rento.common.exception.ErrorType;
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
    public void register(MemberRegisterRequest request) {
        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new DomainException(ErrorType.DEPARTMENT_NOT_FOUND));

        Company company = companyRepository.findByCompanyCode(request.companyCode())
                .orElseThrow(() -> new DomainException(ErrorType.COMPANY_NOT_FOUND));

        String password = passwordEncoder.encode(request.password());

        validateDuplicatePhoneNumber(request.phoneNumber(), request.companyCode());

        Member member =  MemberRegisterRequest.toEntity(request, password, department, company);

        memberRepository.save(member);
    }

    @Transactional
    public void update(MemberUpdateRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new DomainException(ErrorType.MEMBER_NOT_FOUND));

        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new DomainException(ErrorType.DEPARTMENT_NOT_FOUND));

        validateDuplicatePhoneNumber(request.phoneNumber(), request.companyCode());

        member.update(request.name(), request.email(), request.getPosition(), request.loginId(), request.phoneNumber(), department);
    }

    @Transactional
    public void delete(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new DomainException(ErrorType.MEMBER_NOT_FOUND));

        memberRepository.delete(member);
    }

    @Transactional(readOnly = true)
    public List<MemberInfoResponse> getMemberList(String companyCode) {
        Company company = companyRepository.findByCompanyCode(companyCode)
                .orElseThrow(() -> new DomainException(ErrorType.COMPANY_NOT_FOUND));

        return memberRepository.findAllByCompanyId(company.getId()).stream()
                .map(MemberInfoResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public MemberInfoResponse getMember(Long memberId) {
        Member member =  memberRepository.findById(memberId)
                .orElseThrow(() -> new DomainException(ErrorType.MEMBER_NOT_FOUND));

        return MemberInfoResponse.from(member);
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
