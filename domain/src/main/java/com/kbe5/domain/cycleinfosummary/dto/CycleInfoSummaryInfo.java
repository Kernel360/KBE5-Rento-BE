package com.kbe5.domain.cycleinfosummary.dto;

import com.kbe5.domain.cycleinfosummary.entity.CycleInfoSummary;
import com.kbe5.domain.event.enums.GpsCondition;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class CycleInfoSummaryInfo {
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

    public static CycleInfoSummaryInfo fromEntity(CycleInfoSummary cycleInfoSummary) {
        return CycleInfoSummaryInfo.builder()
                .id(cycleInfoSummary.getId())
                .mdn(cycleInfoSummary.getMdn())
                .cycleInfoTime(cycleInfoSummary.getCycleInfoTime())
                .driveId(cycleInfoSummary.getDriveId())
                .sec(cycleInfoSummary.getSec())
                .gpsCondition(cycleInfoSummary.getGpsCondition())
                .latitude(cycleInfoSummary.getLatitude())
                .longitude(cycleInfoSummary.getLongitude())
                .angle(cycleInfoSummary.getAngle())
                .speed(cycleInfoSummary.getSpeed())
                .sum(cycleInfoSummary.getSum())
                .battery(cycleInfoSummary.getBattery())
                .build();
    }
}
