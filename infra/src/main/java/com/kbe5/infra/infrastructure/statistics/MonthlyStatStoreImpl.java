package com.kbe5.infra.infrastructure.statistics;

import com.kbe5.domain.statistics.entity.MonthlyStats;
import com.kbe5.domain.statistics.service.MonthlyStatStore;
import com.kbe5.infra.infrastructure.statistics.repository.MonthlyStatsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MonthlyStatStoreImpl implements MonthlyStatStore {

    private final MonthlyStatsRepository monthlyStatsRepository;

    @Override
    public void save(MonthlyStats newStats) {
        monthlyStatsRepository.save(newStats);
    }
}
