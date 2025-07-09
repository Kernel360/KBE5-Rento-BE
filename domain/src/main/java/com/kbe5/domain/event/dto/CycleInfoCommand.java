package com.kbe5.domain.event.dto;

import com.kbe5.domain.event.enums.GpsCondition;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class CycleInfoCommand {
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
}
