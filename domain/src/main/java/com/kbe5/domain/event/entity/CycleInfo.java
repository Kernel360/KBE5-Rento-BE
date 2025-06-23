package com.kbe5.domain.event.entity;

import com.kbe5.domain.event.enums.GpsCondition;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "cycle_info")
@IdClass(CycleInfoId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CycleInfo {

    @Id
    private Long mdn;

    @Id
    private LocalDateTime cycleInfoTime;

    private Long driveId;

    private Integer sec;

    @Enumerated(value = EnumType.STRING)
    private GpsCondition gpsCondition;

    @Column(precision = 9, scale = 6)
    private BigDecimal latitude;

    @Column(precision = 9, scale = 6)
    private BigDecimal longitude;

    private Integer angle;

    private Integer speed;

    private Long sum;

    private Integer battery;

    @Builder
    public CycleInfo(LocalDateTime cycleInfoTime, Long mdn, Long driveId, Integer sec,
        GpsCondition gpsCondition, BigDecimal latitude, BigDecimal longitude, Integer angle, Integer speed, Long sum,
        Integer battery) {
        this.cycleInfoTime = cycleInfoTime;
        this.mdn = mdn;
        this.driveId = driveId;
        this.sec = sec;
        this.gpsCondition = gpsCondition;
        this.latitude = latitude;
        this.longitude = longitude;
        this.angle = angle;
        this.speed = speed;
        this.sum = sum;
        this.battery = battery;
    }
}
