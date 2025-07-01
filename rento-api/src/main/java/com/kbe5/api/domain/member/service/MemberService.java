package com.kbe5.api.domain.member.service;


import com.kbe5.api.domain.member.dto.request.MemberUpdateRequest;
import com.kbe5.api.domain.member.dto.response.MemberInfoResponse;
import com.kbe5.api.domain.member.vo.MemberUpdateVO;
import com.kbe5.common.exception.DomainException;
import com.kbe5.common.exception.ErrorType;
import com.kbe5.domain.company.repository.CompanyRepository;
import com.kbe5.domain.department.entity.Department;
import com.kbe5.domain.department.repository.DepartmentRepository;
import com.kbe5.domain.manager.entity.Manager;
import com.kbe5.domain.member.entity.Member;
import com.kbe5.domain.member.entity.Position;
import com.kbe5.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final CompanyRepository companyRepository;

    @Transactional
    public Member register(Member member, Long departmentId) {
        validateDuplicate(member); // 중복 검증

        member.encodePassword(passwordEncoder);

        member.assignCompany(companyRepository.findByCompanyCode(member.getCompanyCode()).orElseThrow(
                () -> new DomainException(ErrorType.COMPANY_NOT_FOUND))
        );
        member.assignDepartment(departmentRepository.findById(departmentId).orElseThrow(
                () -> new DomainException(ErrorType.DEPARTMENT_NOT_FOUND))
        );

        return memberRepository.save(member);
    }

    private void validateDuplicate(Member member) {
        if (isExistPhoneNumber(member.getCompanyCode(), member.getPhoneNumber())) {
            throw new DomainException(ErrorType.DUPLICATE_PHONE_NUMBER);
        }
        if (isExistEmail(member.getCompanyCode(), member.getEmail())) {
            throw new DomainException(ErrorType.DUPLICATE_EMAIL);
        }
        if (isExistLoginId(member.getCompanyCode(), member.getLoginId())) {
            throw new DomainException(ErrorType.DUPLICATE_LOGIN_ID);
        }
    }


    @Transactional
    public MemberInfoResponse update(Manager manager, MemberUpdateRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new DomainException(ErrorType.MEMBER_NOT_FOUND));

        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new DomainException(ErrorType.DEPARTMENT_NOT_FOUND));

        if (!manager.getCompany().getId().equals(member.getCompany().getId())) {
            throw new DomainException(ErrorType.NOT_AUTHORIZED);
        }

        // 중복 체크 (자기 자신은 제외)
        if (memberRepository.existsByEmailAndIdNot(request.email(), memberId)) {
            throw new DomainException(ErrorType.DUPLICATE_EMAIL);
        }

        if (memberRepository.existsByLoginIdAndIdNot(request.loginId(), memberId)) {
            throw new DomainException(ErrorType.DUPLICATE_LOGIN_ID);
        }

        if (memberRepository.existsByPhoneNumberAndIdNot(request.phoneNumber(), memberId)) {
            throw new DomainException(ErrorType.DUPLICATE_PHONE_NUMBER);
        }

        MemberUpdateVO vo = new MemberUpdateVO(request);
        vo.toUpdate(member, department);

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
    public Page<Member> getMemberList(
            Manager manager,
            Position position,
            Long departmentId,
            String search,
            Pageable pageable
    ) {
        return memberRepository.findMembersByConditions(manager.getCompany().getId(), position, departmentId, search, pageable);
    }

    @Transactional(readOnly = true)
    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new DomainException(ErrorType.MEMBER_NOT_FOUND));
    }

    public boolean isExistLoginId(String companyCode, String loginId) {
        return memberRepository.existsByCompanyCodeAndLoginId(companyCode, loginId);
    }

    public boolean isExistEmail(String companyCode, String email) {
        return memberRepository.existsByCompanyCodeAndEmail(companyCode, email);
    }

    public boolean isExistPhoneNumber(String companyCode, String phoneNumber) {
        return memberRepository.existsByCompanyCodeAndPhoneNumber(companyCode, phoneNumber);
    }
}
