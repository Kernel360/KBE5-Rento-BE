package com.kbe5.domain.statistics.service;

import com.kbe5.domain.statistics.entity.MonthlyStats;

import java.util.Optional;

public interface MonthlyStatReader {
    Optional<MonthlyStats> findByCompanyCodeAndYearAndMonth(String companyCode, int year, int month);
}
