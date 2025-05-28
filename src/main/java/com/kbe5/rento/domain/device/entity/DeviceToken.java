package com.kbe5.rento.domain.device.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Entity
@Table(name = "device_tokens")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeviceToken {

    @Id
    private String token;

    private Long deviceId;

    private Long mdn;

    private LocalDateTime createdAt;

    private Long exPeriod;

    @Builder
    private DeviceToken(String token, Long deviceId, Long mdn, LocalDateTime createdAt, Long exPeriod) {
        this.token = token;
        this.deviceId = deviceId;
        this.mdn = mdn;
        this.createdAt = createdAt;
        this.exPeriod = exPeriod;
    }

    public static DeviceToken of(String token, Device device, LocalDateTime createdAt, Long exPeriod) {
        return DeviceToken.builder()
            .token(token)
            .deviceId(device.getId())
            .mdn(device.getMdn())
            .createdAt(createdAt)
            .exPeriod(exPeriod)
            .build();
    }
}
