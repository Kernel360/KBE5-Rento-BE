package com.kbe5.rento.domain.cycleInfoSummary.entity;

import com.kbe5.rento.domain.event.entity.CycleInfo;
import com.kbe5.rento.domain.event.enums.GpsCondition;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CycleInfoSummary {

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
    public CycleInfoSummary(CycleInfo cycleInfo) {
        this.mdn = cycleInfo.getMdn();
        this.cycleInfoTime = cycleInfo.getCycleInfoTime();
        this.driveId = cycleInfo.getDriveId();
        this.sec = cycleInfo.getSec();
        this.gpsCondition = cycleInfo.getGpsCondition();
        this.latitude = cycleInfo.getLatitude();
        this.longitude = cycleInfo.getLongitude();
        this.angle = cycleInfo.getAngle();
        this.speed = cycleInfo.getSpeed();
        this.sum = cycleInfo.getSum();
        this.battery = cycleInfo.getBattery();
    }

}
