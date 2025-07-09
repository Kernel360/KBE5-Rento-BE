package com.kbe5.domain.event.dto;

import com.kbe5.domain.device.entity.DeviceToken;
import com.kbe5.domain.event.entity.CycleEvent;
import com.kbe5.domain.event.entity.CycleInfo;
import com.kbe5.domain.event.entity.OnOffEvent;
import com.kbe5.domain.event.enums.EventType;
import com.kbe5.domain.event.enums.GpsCondition;
import com.kbe5.domain.exception.DeviceException;
import com.kbe5.domain.exception.DeviceResultCode;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

public class EventCommand {
    @Getter
    @SuperBuilder
    public static abstract class Event{
        private Long mdn;
        private LocalDateTime oTime;
        private Long driveId;
        private String terminalId;
        private Integer makerId;
        private Integer packetVersion;
        private Integer deviceId;
        private GpsCondition gpsCondition;
        private BigDecimal latitude;
        private BigDecimal longitude;
        private Integer angle;
        private Integer speed;
        private Long currentAccumulatedDistance;
        private EventType eventType;

    }

    @Getter
    @Builder
    public static class CycleInfoCommand{
        private Long driveId;
        private Integer sec;
        private GpsCondition gpsCondition;
        private BigDecimal latitude;
        private BigDecimal longitude;
        private Integer angle;
        private Integer speed;
        private Long sum;
        private Integer battery;

        public CycleInfo of(LocalDateTime oTime, Long mdn, DeviceToken deviceToken) {
            return CycleInfo.builder()
                .cycleInfoTime(oTime.plusSeconds(this.sec))
                .mdn(mdn)
                .driveId(deviceToken.getDriveId())
                .sec(this.sec)
                .gpsCondition(this.gpsCondition)
                .longitude(this.longitude)
                .latitude(this.latitude)
                .angle(this.angle)
                .speed(this.speed)
                .sum(this.sum)
                .battery(this.battery)
                .build();
        }
    }

    @Getter
    @SuperBuilder
    public static class CycleEventCommand extends Event{
        private Integer cycleCount;
        private List<EventCommand.CycleInfoCommand> cycleInfoCommands;

        public CycleEvent of(DeviceToken token, Long mdn, List<CycleInfo> cycleInfos) {
            if (cycleInfos == null || cycleInfos.isEmpty()) {
                throw new DeviceException(DeviceResultCode.REQUIRED_PARAMETER_ERROR);
            }

            return CycleEvent.builder()
                .createdAt(LocalDateTime.now())
                .oTime(cycleInfos.get(0).getCycleInfoTime())
                .mdn(mdn)
                .terminalId(super.terminalId)
                .makerId(super.makerId)
                .packetVersion(super.packetVersion)
                .deviceId(super.deviceId)
                .cycleCount(this.cycleCount)
                .eventType(EventType.CYCLE_INFO)
                .driveId(token.getDriveId())
                .cycleInfos(cycleInfos)
                .build();
        }

        public List<CycleInfo> toCycleInfoEntities(DeviceToken token) {
            return this.cycleInfoCommands.stream()
                .map(req -> req.of(super.oTime, super.mdn, token))
                .toList();
        }
    }

    @Getter
    @SuperBuilder
    public static class GeofenceEvent extends Event{
        private Integer geoGrpId;
        private Integer geoPid;
        private Integer evtVal;
    }

    @Getter
    @SuperBuilder
    public static class OnEventCommand extends Event{
        private LocalDateTime onTime;
        private LocalDateTime offTime;
        private Integer batteryVolt;

        public OnOffEvent toEntity(DeviceToken token) {
            return OnOffEvent.builder()
                .createdAt(LocalDateTime.now())
                .oTime(this.onTime)
                .mdn(super.mdn)
                .terminalId(super.terminalId)
                .makerId(super.makerId)
                .packetVersion(super.packetVersion)
                .deviceId(super.deviceId)
                .gpsCondition(super.gpsCondition)
                .latitude(super.latitude)
                .longitude(super.longitude)
                .angle(super.angle)
                .speed(super.speed)
                .currentAccumulatedDistance(super.currentAccumulatedDistance)
                .onTime(this.onTime)
                .offTime(this.offTime)
                .eventType(EventType.ON)
                .driveId(token.getDriveId())
                .build();
        }
    }

    @Getter
    @SuperBuilder
    public static class OffEventCommand extends Event{

        private LocalDateTime onTime;
        private LocalDateTime offTime;
        private Integer batteryVolt;

        public OnOffEvent toEntity(DeviceToken token) {
            return OnOffEvent.builder()
                .createdAt(LocalDateTime.now())
                .oTime(this.offTime)
                .mdn(super.mdn)
                .gpsCondition(super.gpsCondition)
                .latitude(super.latitude)
                .longitude(super.longitude)
                .angle(super.angle)
                .speed(super.speed)
                .currentAccumulatedDistance(super.currentAccumulatedDistance)
                .onTime(this.onTime)
                .offTime(this.offTime)
                .eventType(EventType.OFF)
                .driveId(token.getDriveId())
                .build();
        }
    }

}
