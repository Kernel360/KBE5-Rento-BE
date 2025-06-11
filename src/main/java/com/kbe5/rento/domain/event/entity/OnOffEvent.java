package com.kbe5.rento.domain.event.entity;

import com.kbe5.rento.common.exception.DeviceException;
import com.kbe5.rento.domain.device.enums.DeviceResultCode;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Entity
@SuperBuilder
@DiscriminatorValue("ON_OFF")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OnOffEvent extends Event {

    LocalDateTime onTime;

    LocalDateTime offTime;

    private Integer batteryVolt;
}
