package com.kbe5.rento.domain.member.repository;

import com.kbe5.rento.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAllByDepartmentId(Long id);

    List<Member> findAllByCompanyId(Long id);

    Member findByPhoneNumberAndCompanyId(String phoneNumber, Long id);

    boolean existsByPhoneNumberAndCompanyId(String phoneNumber, Long id);

    boolean existsByEmailAndCompanyId(String email, Long id);

    boolean existsByLoginIdAndCompanyId(String loginId, Long id);

    Member findByEmailAndCompanyId(String email, Long id);

    Member findByLoginIdAndCompanyId(String loginId, Long id);
}
