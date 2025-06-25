package com.kbe5.domain.statistics.repository;

import com.kbe5.domain.statistics.entity.MonthlyStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MonthlyStatsRepository extends JpaRepository<MonthlyStats, Long> {

    @Query("""
        SELECT m 
        FROM MonthlyStats m 
        WHERE m.companyCode = :companyCode 
        AND m.year = :year 
        AND m.month = :month
""")
    Optional<MonthlyStats> findByCompanyCodeAndYearAndMonth(@Param("companyCode") String companyCode,
                                                            @Param("year") int year,
                                                            @Param("month") int month
    );
}
