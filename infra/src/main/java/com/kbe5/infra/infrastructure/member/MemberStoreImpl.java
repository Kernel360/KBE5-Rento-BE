package com.kbe5.infra.infrastructure.member;

import com.kbe5.domain.company.service.CompanyReader;
import com.kbe5.domain.department.entity.Department;
import com.kbe5.domain.exception.DomainException;
import com.kbe5.domain.exception.ErrorType;
import com.kbe5.domain.member.entity.Member;
import com.kbe5.domain.member.service.MemberStore;
import com.kbe5.infra.infrastructure.drive.repository.DriveRepository;
import com.kbe5.infra.infrastructure.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class MemberStoreImpl implements MemberStore {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CompanyReader companyReader;
    private final DriveRepository driveRepository;

    @Override
    public Member store(Member member, Department department) {
        member.assignCompany(companyReader.findByCompanyCode(member.getCompanyCode()));
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
