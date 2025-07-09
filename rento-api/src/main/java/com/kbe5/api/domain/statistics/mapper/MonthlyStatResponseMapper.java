package com.kbe5.api.domain.statistics.mapper;

import com.kbe5.api.domain.statistics.dto.MonthlyStatsResponse;
import com.kbe5.domain.statistics.dto.MonthlyStatInfo;
import org.springframework.stereotype.Component;

@Component
public class MonthlyStatResponseMapper {
    public MonthlyStatsResponse toResponse(MonthlyStatInfo monthlyStatInfo) {
        return new MonthlyStatsResponse(
                monthlyStatInfo.getYear(),
                monthlyStatInfo.getMonth(),
                monthlyStatInfo.getTotalDistance(),
                monthlyStatInfo.getTotalDrivingTime(),
                monthlyStatInfo.getTotalDrivingCnt(),
                monthlyStatInfo.getAvgSpeed(),
                monthlyStatInfo.getBusinessRatio(),
                monthlyStatInfo.getCommuteRatio(),
                monthlyStatInfo.getNonBusinessRatio()
        );
    }
}
