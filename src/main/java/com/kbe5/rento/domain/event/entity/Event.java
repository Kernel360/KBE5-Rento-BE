package com.kbe5.rento.domain.event.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbe5.rento.common.exception.DeviceException;
import com.kbe5.rento.domain.device.enums.DeviceResultCode;
import com.kbe5.rento.domain.device.enums.GpsCondition;
import com.kbe5.rento.domain.event.enums.EventType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Entity
@SuperBuilder
@Table(name = "events")
@IdClass(EventId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
public abstract class Event {

    @Id
    @JsonProperty("oTime")
    private LocalDateTime oTime;

    @Id
    Long mdn;

    String terminalId;

    Integer makerId;

    Integer packetVersion;

    Integer deviceId;

    Long driveId;

    @Enumerated(EnumType.STRING)
    private GpsCondition gpsCondition;

    @Column(precision = 9, scale = 6)
    private BigDecimal latitude;

    @Column(precision = 9, scale = 6)
    private BigDecimal longitude;

    private Integer angle;

    private Integer speed;

    private Long currentAccumulatedDistance; //총 누적 거리

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    public void validateMdnMatch(Long mdn) {
        if (!this.mdn.equals(mdn)) {
            throw new DeviceException(DeviceResultCode.MISMATCHED_MDN);
        }
    }
}
