package com.kbe5.infra.infrastructure.member;

import com.kbe5.domain.exception.DomainException;
import com.kbe5.domain.exception.ErrorType;
import com.kbe5.domain.member.entity.Member;
import com.kbe5.domain.member.entity.Position;
import com.kbe5.domain.member.repository.MemberRepository;
import com.kbe5.domain.member.service.MemberReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberReadImpl implements MemberReader {

    private final MemberRepository memberRepository;

    @Override
    public Page<Member> getMembersByConditions(Long id, Position position, Long departmentId, String search, Pageable pageable) {
        return memberRepository.findMembersByConditions(id,position,departmentId,search,pageable);
    }

    @Override
    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(()->new DomainException(ErrorType.MEMBER_NOT_FOUND));
    }

    @Override
    public boolean existsByCompanyCodeAndLoginId(String companyCode, String loginId) {
        return memberRepository.existsByCompanyCodeAndLoginId(companyCode, loginId);
    }

    @Override
    public boolean existsByCompanyCodeAndEmail(String companyCode, String email) {
        return memberRepository.existsByCompanyCodeAndEmail(companyCode, email);
    }

    @Override
    public boolean existsByCompanyCodeAndPhoneNumber(String companyCode, String phoneNumber) {
        return memberRepository.existsByCompanyCodeAndPhoneNumber(companyCode, phoneNumber);
    }

    @Override
    public List<Member> findAllByDepartmentId(Long departmentId) {
        return memberRepository.findAllByDepartmentId(departmentId);
    }
}
