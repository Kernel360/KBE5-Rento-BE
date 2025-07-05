package com.kbe5.domain.member.service;

import com.kbe5.domain.member.entity.Member;
import com.kbe5.domain.member.entity.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberReader {
    Page<Member> getMembersByConditions(Long id, Position position, Long departmentId, String search, Pageable pageable);

    Member getMemberById(Long memberId);

    boolean existsByCompanyCodeAndLoginId(String companyCode, String loginId);

    boolean existsByCompanyCodeAndEmail(String companyCode, String email);

    boolean existsByCompanyCodeAndPhoneNumber(String companyCode, String phoneNumber);
}
