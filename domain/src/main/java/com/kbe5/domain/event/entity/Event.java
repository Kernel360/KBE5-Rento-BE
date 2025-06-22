package com.kbe5.domain.event.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbe5.domain.event.enums.EventType;
import com.kbe5.domain.event.enums.GpsCondition;
import com.kbe5.domain.exception.DeviceException;
import com.kbe5.domain.exception.DeviceResultCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private Long mdn;

    @Id
    @JsonProperty("oTime")
    private LocalDateTime oTime;

    private Long driveId;

    private String terminalId;

    private Integer makerId;

    private Integer packetVersion;

    private Integer deviceId;

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
