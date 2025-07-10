package com.kbe5.domain.cycleinfosummary.dto;

import com.kbe5.domain.cycleinfosummary.entity.CycleDataSummary;
import com.kbe5.domain.event.enums.GpsCondition;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class CycleDataSummaryInfo {
    private Long id;
    private Long mdn;
    private LocalDateTime cycleInfoTime;
    private Long driveId;
    private Integer sec;
    private GpsCondition gpsCondition;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer angle;
    private Integer speed;
    private Long sum;
    private Integer battery;

    public static CycleDataSummaryInfo fromEntity(CycleDataSummary cycleDataSummary) {
        return CycleDataSummaryInfo.builder()
                .id(cycleDataSummary.getId())
                .mdn(cycleDataSummary.getMdn())
                .cycleInfoTime(cycleDataSummary.getCycleInfoTime())
                .driveId(cycleDataSummary.getDriveId())
                .sec(cycleDataSummary.getSec())
                .gpsCondition(cycleDataSummary.getGpsCondition())
                .latitude(cycleDataSummary.getLatitude())
                .longitude(cycleDataSummary.getLongitude())
                .angle(cycleDataSummary.getAngle())
                .speed(cycleDataSummary.getSpeed())
                .sum(cycleDataSummary.getSum())
                .battery(cycleDataSummary.getBattery())
                .build();
    }
}
