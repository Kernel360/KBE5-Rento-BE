package com.kbe5.api.domain.statistics.dto;

import com.kbe5.domain.statistics.entity.MonthlyStats;

public record MonthlyStatsResponse(
        int year,
        int month,
        long totalDistance,
        long totalDrivingTime,
        int totalDrivingCnt,
        double avgSpeed,
        double businessRatio,
        double commuteRatio,
        double nonBusinessRatio
) {
    public static MonthlyStatsResponse fromEntity(MonthlyStats monthlyStats) {
        return new MonthlyStatsResponse(
                monthlyStats.getYear(),
                monthlyStats.getMonth(),
                monthlyStats.getTotalDistance(),
                monthlyStats.getTotalDrivingTime(),
                monthlyStats.getTotalDrivingCnt(),
                monthlyStats.getAvgSpeed(),
                monthlyStats.getBusinessRatio(),
                monthlyStats.getCommuteRatio(),
                monthlyStats.getNonBusinessRatio()
        );
    }
}
