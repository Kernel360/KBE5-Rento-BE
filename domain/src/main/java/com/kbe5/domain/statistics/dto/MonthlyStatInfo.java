package com.kbe5.domain.statistics.dto;

import com.kbe5.domain.statistics.entity.MonthlyStats;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder
@AllArgsConstructor
public class MonthlyStatInfo {
    private final int year;
    private final int month;
    private final long totalDistance;
    private final long totalDrivingTime;
    private final int totalDrivingCnt;
    private final double avgSpeed;
    private final double businessRatio;
    private final double commuteRatio;
    private final double nonBusinessRatio;

    public static Optional<MonthlyStatInfo> fromEntity(MonthlyStats entity){
        if(entity == null){
            return Optional.empty();
        }
        return Optional.of(MonthlyStatInfo.builder()
                .year(entity.getYear())
                .month(entity.getMonth())
                .totalDistance(entity.getTotalDistance())
                .totalDrivingTime(entity.getTotalDrivingTime())
                .totalDrivingCnt(entity.getTotalDrivingCnt())
                .avgSpeed(entity.getAvgSpeed())
                .businessRatio(entity.getBusinessRatio())
                .commuteRatio(entity.getCommuteRatio())
                .nonBusinessRatio(entity.getNonBusinessRatio())
                .build());
    }
}
