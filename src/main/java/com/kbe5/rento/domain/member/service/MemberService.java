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
import com.kbe5.rento.domain.member.entity.Position;
import com.kbe5.rento.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final CompanyRepository companyRepository;

    @Transactional
    public Member register(Member member, Long departmentId) {
        validateDuplicateFields(member.getPhoneNumber(), member.getEmail(), member.getLoginId(), member.getCompanyCode());

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

        validateDuplicateFields(request.phoneNumber(), request.email(), request.loginId(), request.companyCode(), memberId);

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

    // 회원가입 시 중복 검증 (새 등록)
    private void validateDuplicateFields(String phoneNumber, String email, String loginId, String companyCode) {
        Company company = companyRepository.findByCompanyCode(companyCode).orElseThrow(
                () -> new DomainException(ErrorType.COMPANY_NOT_FOUND)
        );

        // 전화번호 중복 체크
        if(memberRepository.existsByPhoneNumberAndCompanyId(phoneNumber, company.getId())) {
            throw new DomainException(ErrorType.DUPLICATE_PHONE_NUMBER);
        }

        // 이메일 중복 체크
        if(memberRepository.existsByEmailAndCompanyId(email, company.getId())) {
            throw new DomainException(ErrorType.DUPLICATE_EMAIL);
        }

        // 로그인 아이디 중복 체크
        if(memberRepository.existsByLoginIdAndCompanyId(loginId, company.getId())) {
            throw new DomainException(ErrorType.DUPLICATE_LOGIN_ID);
        }
    }

    // 회원 업데이트 시 중복 검증 (자신 제외)
    private void validateDuplicateFields(String phoneNumber, String email, String loginId, String companyCode, Long memberId) {
        Company company = companyRepository.findByCompanyCode(companyCode).orElseThrow(
                () -> new DomainException(ErrorType.COMPANY_NOT_FOUND)
        );

        // 전화번호 중복 체크 (자신 제외)
        Member memberWithSamePhone = memberRepository.findByPhoneNumberAndCompanyId(phoneNumber, company.getId());
        if(memberWithSamePhone != null && !Objects.equals(memberWithSamePhone.getId(), memberId)) {
            throw new DomainException(ErrorType.DUPLICATE_PHONE_NUMBER);
        }

        // 이메일 중복 체크 (자신 제외)
        Member memberWithSameEmail = memberRepository.findByEmailAndCompanyId(email, company.getId());
        if(memberWithSameEmail != null && !Objects.equals(memberWithSameEmail.getId(), memberId)) {
            throw new DomainException(ErrorType.DUPLICATE_EMAIL);
        }

        // 로그인 아이디 중복 체크 (자신 제외)
        Member memberWithSameLoginId = memberRepository.findByLoginIdAndCompanyId(loginId, company.getId());
        if(memberWithSameLoginId != null && !Objects.equals(memberWithSameLoginId.getId(), memberId)) {
            throw new DomainException(ErrorType.DUPLICATE_LOGIN_ID);
        }
    }
}