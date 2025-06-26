package com.kbe5.domain.member.repository;

import com.kbe5.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAllByDepartmentId(Long id);

    Page<Member> findAllByCompanyId(Long id, Pageable pageable);
    
    boolean existsByLoginId(String loginId);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmailAndIdNot(String email, Long memberId);

    boolean existsByLoginIdAndIdNot(String loginId, Long memberId);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long memberId);
}
