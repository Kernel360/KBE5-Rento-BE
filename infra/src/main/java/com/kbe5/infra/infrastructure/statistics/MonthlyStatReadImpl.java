package com.kbe5.infra.infrastructure.statistics;

import com.kbe5.domain.statistics.entity.MonthlyStats;
import com.kbe5.domain.statistics.service.MonthlyStatReader;
import com.kbe5.infra.infrastructure.statistics.repository.MonthlyStatsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class MonthlyStatReadImpl implements MonthlyStatReader {

    private final MonthlyStatsRepository monthlyStatsRepository;

    @Override
    public Optional<MonthlyStats> findByCompanyCodeAndYearAndMonth(String companyCode, int year, int month) {
        return monthlyStatsRepository.findByCompanyCodeAndYearAndMonth(companyCode, year, month);
    }
}
