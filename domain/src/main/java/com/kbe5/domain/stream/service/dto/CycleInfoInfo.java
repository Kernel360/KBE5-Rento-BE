package com.kbe5.domain.stream.service.dto;

import com.kbe5.domain.event.entity.CycleInfo;
import com.kbe5.domain.event.enums.GpsCondition;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class CycleInfoInfo {
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

    public static CycleInfoInfo fromCycleInfo(CycleInfo cycleInfo) {
        return CycleInfoInfo.builder()
                .tsid(cycleInfo.getTsid())
                .mdn(cycleInfo.getMdn())
                .cycleInfoTime(cycleInfo.getCycleInfoTime())
                .driveId(cycleInfo.getDriveId())
                .sec(cycleInfo.getSec())
                .gpsCondition(cycleInfo.getGpsCondition())
                .latitude(cycleInfo.getLatitude())
                .longitude(cycleInfo.getLongitude())
                .angle(cycleInfo.getAngle())
                .speed(cycleInfo.getSpeed())
                .sum(cycleInfo.getSum())
                .battery(cycleInfo.getBattery())
                .build();
    }
}
