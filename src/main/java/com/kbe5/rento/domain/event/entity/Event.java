package com.kbe5.rento.domain.event.entity;

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
@Table(name = "events")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "event_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Event {

    @Id
    @GeneratedValue
    Long id;

    Long deviceUniqueId;

    Long mdn;

    String terminalId;

    Integer makerId;

    Integer packetVersion;

    Integer deviceId;

    @Enumerated(EnumType.STRING)
    private GpsCondition gpsCondition;

    @Column(precision = 9, scale = 6)
    private BigDecimal latitude;

    @Column(precision = 9, scale = 6)
    private BigDecimal longitude;

    private Integer angle;

    private Integer speed;

    private Long currentAccumulatedDistance; //총 누적 거리

}
