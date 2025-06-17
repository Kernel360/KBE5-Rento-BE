package com.kbe5.rento.domain.device.entity;

import com.kbe5.rento.common.exception.DeviceException;
import com.kbe5.rento.domain.device.enums.DeviceResultCode;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Entity
@ToString
@Table(name = "device_tokens")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeviceToken {

    @Id
    private String token;

    private Long deviceId;

    private Long mdn;

    private Long createdMs;

    private Long exPeriod;

    private Long driveId;

    @Builder
    private DeviceToken(String token, Long deviceId, Long mdn, Long createdMs, Long exPeriod, Long driveId) {
        this.token = token;
        this.deviceId = deviceId;
        this.mdn = mdn;
        this.createdMs = createdMs;
        this.exPeriod = exPeriod;
        this.driveId = driveId;
    }

    public static DeviceToken of(String token, Device device, Long createdMs, Long exPeriod, Long driveId) {
        return DeviceToken.builder()
            .token(token)
            .deviceId(device.getId())
            .mdn(device.getMdn())
            .createdMs(createdMs)
            .exPeriod(exPeriod)
            .driveId(driveId)
            .build();
    }

    public void validateNotExpired() {
        if (createdMs == null || exPeriod == null) {
            throw new DeviceException(DeviceResultCode.INVALID_TOKEN);
        }
        if (createdMs + exPeriod < System.currentTimeMillis()) {
            throw new DeviceException(DeviceResultCode.INVALID_TOKEN);
        }
    }
}
