package com.kbe5.rento.domain.event.dto.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kbe5.rento.common.datetime.EventLocalDateTimeDeserializer;
import com.kbe5.rento.domain.device.enums.GpsCondition;
import com.kbe5.rento.domain.event.entity.OnOffEvent;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.ToString;

@Builder
public record OnOffEventMessage(

    Long mdn,

    Long deviceUniqueId,

    String terminalId,

    Integer makerId,

    Integer packetVersion,

    Integer deviceId,

    LocalDateTime onTime,

    LocalDateTime offTime,

    GpsCondition gpsCondition, // GPS 상태

    BigDecimal latitude,

    BigDecimal longitude,

    Integer angle,

    Integer speed,

    Long sum
) {

    public static OnOffEventMessage fromEntity(OnOffEvent onOffEvent) {
        return OnOffEventMessage.builder()
            .mdn(onOffEvent.getMdn())
            .terminalId(onOffEvent.getTerminalId())
            .makerId(onOffEvent.getMakerId())
            .packetVersion(onOffEvent.getPacketVersion())
            .deviceId(onOffEvent.getDeviceId())
            .deviceUniqueId(onOffEvent.getDeviceUniqueId())
            .gpsCondition(onOffEvent.getGpsCondition())
            .latitude(onOffEvent.getLatitude())
            .longitude(onOffEvent.getLongitude())
            .angle(onOffEvent.getAngle())
            .speed(onOffEvent.getSpeed())
            .sum(onOffEvent.getCurrentAccumulatedDistance())
            .onTime(onOffEvent.getOnTime())
            .offTime(onOffEvent.getOffTime())
            .build();
    }
}
