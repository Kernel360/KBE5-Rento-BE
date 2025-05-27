package com.kbe5.rento.domain.member.repository;

import com.kbe5.rento.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAllByDepartmentId(Long id);

    List<Member> findAllByCompanyId(Long id);

    boolean existsByPhoneNumberAndCompanyId(String phoneNumber, Long id);
}
