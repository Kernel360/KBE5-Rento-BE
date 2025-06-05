package com.kbe5.rento.domain.device.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class GeofenceControlInfo {

    private long geoControlId;
    private int updateValue;
    private int geofenceGroupId;
    private int geoEventType; // 1: 진입만 판단, 2: 이탈만 판단, 3: 진입/이탈 모두 판단
    private int geofenceRange;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private LocalDateTime onTime;
    private LocalDateTime offTime;
    private short storeType; // 1: 추가, 2: 삭제
}
