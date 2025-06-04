package com.kbe5.rento.domain.event.entity;

import com.kbe5.rento.domain.device.enums.GpsCondition;
import jakarta.persistence.Entity;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long deviceUniqueId;

    private Integer sec;

    private GpsCondition gpsCondition;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private Integer angle;

    private Integer speed;

    private Long sum;

    private Integer battery;

    @Builder
    public CycleInfo(Long id, Long deviceUniqueId, Integer sec, GpsCondition gpsCondition, BigDecimal latitude,
        BigDecimal longitude, Integer angle, Integer speed, Long sum, Integer battery) {
        this.id = id;
        this.deviceUniqueId = deviceUniqueId;
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
