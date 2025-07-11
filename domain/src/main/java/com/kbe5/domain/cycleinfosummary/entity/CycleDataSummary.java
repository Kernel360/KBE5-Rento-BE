package com.kbe5.domain.cycleinfosummary.entity;


import com.kbe5.domain.event.entity.CycleData;
import com.kbe5.domain.event.enums.GpsCondition;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CycleDataSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // mdn이 필요한가?
    private Long mdn;

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

    // todo: request가 필요한가?
    public CycleDataSummary(CycleData cycleData) {
        this.mdn = cycleData.getMdn();
        this.cycleInfoTime = cycleData.getCycleInfoTime();
        this.driveId = cycleData.getDriveId();
        this.sec = cycleData.getSec();
        this.gpsCondition = cycleData.getGpsCondition();
        this.latitude = cycleData.getLatitude();
        this.longitude = cycleData.getLongitude();
        this.angle = cycleData.getAngle();
        this.speed = cycleData.getSpeed();
        this.sum = cycleData.getSum();
        this.battery = cycleData.getBattery();
    }
}
