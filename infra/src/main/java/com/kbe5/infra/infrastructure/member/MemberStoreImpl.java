package com.kbe5.infra.infrastructure.member;

import com.kbe5.domain.company.repository.CompanyRepository;
import com.kbe5.domain.department.entity.Department;
import com.kbe5.domain.department.repository.DepartmentRepository;
import com.kbe5.domain.drive.repository.DriveRepository;
import com.kbe5.domain.exception.DomainException;
import com.kbe5.domain.exception.ErrorType;
import com.kbe5.domain.member.entity.Member;
import com.kbe5.domain.member.repository.MemberRepository;
import com.kbe5.domain.member.service.MemberStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class MemberStoreImpl implements MemberStore {

    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final CompanyRepository companyRepository;
    private final DriveRepository driveRepository;

    @Override
    public Member store(Member member, Department department) {
        member.assignCompany(companyRepository.findByCompanyCode(member.getCompanyCode())
                .orElseThrow(() -> new DomainException(ErrorType.COMPANY_NOT_FOUND)));
        member.encodePassword(passwordEncoder);
        member.assignDepartment(department);

        return memberRepository.save(member);
    }

    @Override
    public void delete(Member member) {
        //todo: Drvie ddd 되면 바꾸기
        if (driveRepository.existsOngoingDriveByMemberId(member.getId())) {
            throw new DomainException(ErrorType.MEMBER_HAS_ACTIVE_DRIVES);
        }

        memberRepository.delete(member);
    }
}
