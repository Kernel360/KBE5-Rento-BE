package com.kbe5.domain.member.service;

import com.kbe5.domain.member.entity.Member;
import com.kbe5.domain.member.entity.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberReader {
    Page<Member> getMembersByConditions(Long id, Position position, Long departmentId, String search, Pageable pageable);

    Member getMemberById(Long memberId);

    boolean existsByCompanyCodeAndLoginId(String companyCode, String loginId);

    boolean existsByCompanyCodeAndEmail(String companyCode, String email);

    boolean existsByCompanyCodeAndPhoneNumber(String companyCode, String phoneNumber);

    List<Member> findAllByDepartmentId(Long departmentId);

    boolean existsByCompanyCodeAndLoginIdExcludingId(String companyCode, String loginId, Long excludeId);

    boolean existsByCompanyCodeAndEmailExcludingId(String companyCode, String email, Long excludeId);

    boolean existsByCompanyCodeAndPhoneNumberExcludingId(String companyCode, String phoneNumber, Long excludeId);
}
