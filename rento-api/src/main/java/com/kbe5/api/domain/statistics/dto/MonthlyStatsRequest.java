package com.kbe5.api.domain.statistics.dto;

public record MonthlyStatsRequest(
        String companyCode,
        int year,
        int month
) {
}
