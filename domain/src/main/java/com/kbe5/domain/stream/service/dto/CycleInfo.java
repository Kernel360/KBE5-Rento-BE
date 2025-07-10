package com.kbe5.domain.stream.service.dto;

import com.kbe5.domain.event.entity.CycleData;
import com.kbe5.domain.event.enums.GpsCondition;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class CycleInfo {
    private Long tsid;
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

    public static CycleInfo fromCycleInfo(CycleData cycleData) {
        return CycleInfo.builder()
                .tsid(cycleData.getTsid())
                .mdn(cycleData.getMdn())
                .cycleInfoTime(cycleData.getCycleInfoTime())
                .driveId(cycleData.getDriveId())
                .sec(cycleData.getSec())
                .gpsCondition(cycleData.getGpsCondition())
                .latitude(cycleData.getLatitude())
                .longitude(cycleData.getLongitude())
                .angle(cycleData.getAngle())
                .speed(cycleData.getSpeed())
                .sum(cycleData.getSum())
                .battery(cycleData.getBattery())
                .build();
    }
}
