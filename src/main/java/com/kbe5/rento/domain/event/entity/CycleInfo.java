package com.kbe5.rento.domain.event.entity;

import com.kbe5.rento.domain.device.enums.GpsCondition;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "cycle_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CycleInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long mdn;

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
    public CycleInfo(Long id, Long mdn, Integer sec, GpsCondition gpsCondition, BigDecimal latitude,
        BigDecimal longitude, Integer angle, Integer speed, Long sum, Integer battery) {
        this.id = id;
        this.mdn = mdn;
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
