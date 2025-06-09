package com.kbe5.rento.domain.event.entity;

import com.kbe5.rento.domain.device.enums.GpsCondition;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private BigDecimal latitude;

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
