package com.kbe5.rento.domain.device.entity;

import com.kbe5.rento.common.util.BaseEntity;
import com.kbe5.rento.domain.device.dto.request.DeviceRegisterRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@Entity
@Table(name = "devices")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Device extends BaseEntity {

    private Long mobileDeviceNumber;

    private String terminalId;

    private Integer makerId;

    private Integer packetVersion;

    private Integer deviceId;

    private String deviceFirmWareVersion;

    @Builder
    public Device(Long mobileDeviceNumber, String terminalId, Integer makerId, Integer packetVersion,
        Integer deviceId, String deviceFirmWareVersion) {
        this.mobileDeviceNumber = mobileDeviceNumber;
        this.terminalId = terminalId;
        this.makerId = makerId;
        this.packetVersion = packetVersion;
        this.deviceId = deviceId;
        this.deviceFirmWareVersion = deviceFirmWareVersion;
    }

    public static Device from(DeviceRegisterRequest request) {
        return Device.builder()
            .mobileDeviceNumber(request.mobileDeviceNumber())
            .terminalId(request.terminalId())
            .makerId(request.makerId())
            .packetVersion(request.packetVersion())
            .deviceId(request.deviceId())
            .deviceFirmWareVersion("LTE 1.2")
            .build();
    }
}
