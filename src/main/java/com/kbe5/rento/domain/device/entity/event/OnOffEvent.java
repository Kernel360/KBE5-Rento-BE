package com.kbe5.rento.domain.device.entity.event;

import com.kbe5.rento.common.datetime.DateUtil;
import com.kbe5.rento.domain.device.dto.request.OnEventRequest;
import com.kbe5.rento.domain.device.enums.EventType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@Entity
@SuperBuilder
@DiscriminatorValue("ON_OFF")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OnOffEvent extends DeviceEvent {

    LocalDateTime onTime;

    LocalDateTime offTime;

    public static OnOffEvent from(OnEventRequest request) {
        return OnOffEvent.builder()
            .mobileDeviceNumber(request.mobileDeviceNumber())
            .eventType(EventType.ON_OFF)
            .gpsCondition(request.gpsCondition())
            .latitude(request.latitude())
            .longitude(request.longitude())
            .angle(request.angle())
            .speed(request.speed())
            .currentAccumulatedDistance(request.currentAccumulatedDistance())
            .onTime(DateUtil.toOnOffEventLocalDateTime(request.onTime()))
            .offTime(DateUtil.toOnOffEventLocalDateTime(request.offTime()))
            .build();
    }
}
