package com.kbe5.domain.statistics.service;

import com.kbe5.domain.statistics.dto.MonthlyStatInfo;

import java.util.Optional;

public interface MonthlyStatService {
    Optional<MonthlyStatInfo> getStats(String companyCode, int year, int month);

    void generateMonthlyStats();
}
