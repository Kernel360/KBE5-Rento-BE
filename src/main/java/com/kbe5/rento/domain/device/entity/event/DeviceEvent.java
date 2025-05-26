package com.kbe5.rento.domain.device.entity.event;

import com.kbe5.rento.domain.device.enums.EventType;
import com.kbe5.rento.domain.device.enums.GpsCondition;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@SuperBuilder
@Table(name = "device_events")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "event_type", discriminatorType = DiscriminatorType.STRING)
public abstract class DeviceEvent {

    @Id
    @GeneratedValue
    Long id;

    Long mobileDeviceNumber;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Enumerated(EnumType.STRING)
    private GpsCondition gpsCondition;

    @Column(precision = 9, scale = 6)
    private BigDecimal latitude;

    @Column(precision = 9, scale = 6)
    private BigDecimal longitude;

    private Integer angle;

    private Integer speed;

    private Long currentAccumulatedDistance; //총 누적 거리

    private Integer batteryVolt;

}
