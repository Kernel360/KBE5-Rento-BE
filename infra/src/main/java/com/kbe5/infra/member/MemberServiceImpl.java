package com.kbe5.infra.member;

import com.kbe5.domain.department.entity.Department;
import com.kbe5.domain.department.service.DepartmentReader;
import com.kbe5.domain.exception.DomainException;
import com.kbe5.domain.exception.ErrorType;
import com.kbe5.domain.manager.entity.Manager;
import com.kbe5.domain.member.dto.MemberCommand;
import com.kbe5.domain.member.dto.MemberInfo;
import com.kbe5.domain.member.entity.Member;
import com.kbe5.domain.member.entity.Position;
import com.kbe5.domain.member.service.MemberReader;
import com.kbe5.domain.member.service.MemberService;
import com.kbe5.domain.member.service.MemberStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberStore memberStore;
    private final MemberReader memberReader;
    private final DepartmentReader departmentReader;

    @Override
    @Transactional
    public MemberInfo registerMember(MemberCommand.Register command) {
        Member initMember = command.toEntity();
        validateDuplicate(initMember);

        Department department = departmentReader.getDepartmentById(command.getDepartmentId());

        Member member = memberStore.store(initMember, department);

        return MemberInfo.fromEntity(member);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MemberInfo> getMembers(
            Manager manager,
            Position position,
            Long departmentId,
            String search,
            Pageable pageable) {
        Page<Member> memberList = memberReader.getMembersByConditions(
                manager.getCompany().getId(),
                position,
                departmentId,
                search,
                pageable
        );

        return memberList.map(MemberInfo::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberInfo getMember(Long memberId) {
        Member member = memberReader.getMemberById(memberId);

        return MemberInfo.fromEntity(member);
    }

    @Override
    @Transactional
    public MemberInfo updateMember(Long memberId, MemberCommand.Update memberCommand) {
        Member member = memberReader.getMemberById(memberId);
        Department department = departmentReader.getDepartmentById(member.getDepartment().getId());

        member.update(
                memberCommand.getName(),
                memberCommand.getEmail(),
                memberCommand.getPosition(),
                department,
                memberCommand.getPhoneNumber(),
                memberCommand.getLoginId(),
                memberCommand.getCompanyCode()
        );

        return MemberInfo.fromEntity(member);
    }

    @Override
    @Transactional
    public void delete(Long memberId) {
        Member member = memberReader.getMemberById(memberId);

        memberStore.delete(member);
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

    public boolean isExistLoginId(String companyCode, String loginId) {
        return memberReader.existsByCompanyCodeAndLoginId(companyCode, loginId);
    }

    public boolean isExistEmail(String companyCode, String email) {
        return memberReader.existsByCompanyCodeAndEmail(companyCode, email);
    }

    public boolean isExistPhoneNumber(String companyCode, String phoneNumber) {
        return memberReader.existsByCompanyCodeAndPhoneNumber(companyCode, phoneNumber);
    }
}
