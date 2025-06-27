package com.kbe5.domain.member.repository;

import com.kbe5.domain.member.entity.Member;
import com.kbe5.domain.member.entity.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAllByDepartmentId(Long id);

    @Query("SELECT m FROM Member m " +
            "WHERE m.company.id = :companyId " +
            "AND (:position IS NULL OR m.position = :position) " +
            "AND (:departmentId IS NULL OR m.department.id = :departmentId) " +
            "AND (:search IS NULL OR :search = '' OR " +
            "     LOWER(m.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "     LOWER(m.email) LIKE LOWER(CONCAT('%', :search, '%'))" +
            ") " +
            "ORDER BY m.createdAt DESC")
    Page<Member> findMembersByConditions(
            @Param("companyId") Long companyId,
            @Param("position") Position position,
            @Param("departmentId") Long departmentId,
            @Param("search") String search,
            Pageable pageable);

    boolean existsByLoginId(String loginId);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmailAndIdNot(String email, Long memberId);

    boolean existsByLoginIdAndIdNot(String loginId, Long memberId);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long memberId);
}
