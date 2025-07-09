package com.kbe5.domain.statistics.service;

import com.kbe5.domain.statistics.entity.MonthlyStats;

public interface MonthlyStatStore {
    void save(MonthlyStats newStats);
}
