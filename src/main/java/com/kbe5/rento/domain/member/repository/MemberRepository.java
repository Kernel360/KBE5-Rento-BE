package com.kbe5.rento.domain.member.repository;

import com.kbe5.rento.domain.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAllByDepartmentId(Long id);

    List<Member> findAllByCompanyId(Long id);
    
    boolean existsByLoginId(String loginId);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmailAndIdNot(String email, Long memberId);

    boolean existsByLoginIdAndIdNot(String loginId, Long memberId);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long memberId);
}
