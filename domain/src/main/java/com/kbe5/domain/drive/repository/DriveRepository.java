package com.kbe5.domain.drive.repository;

import com.kbe5.domain.company.entity.Company;
import com.kbe5.domain.drive.entity.Drive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DriveRepository extends JpaRepository<Drive, Long> {

    List<Drive> findByMember_Company(Company company);

    @Query("""
      SELECT d.id
      FROM Drive d
      WHERE d.mdn = :mdn
        AND d.startDate <= :startDate
    """)
    Long findIdByMdnAndStartDateBetween(@Param("mdn") Long mdn,
                                        @Param("startDate") LocalDateTime startDate
    );
}
