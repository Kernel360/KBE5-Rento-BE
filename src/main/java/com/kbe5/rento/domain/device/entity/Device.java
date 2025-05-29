package com.kbe5.rento.domain.device.entity;

import com.kbe5.rento.common.util.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Entity
@Table(name = "devices")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Device extends BaseEntity {

    private Long mdn;

    private String terminalId;

    private Integer makerId;

    private Integer packetVersion;

    private Integer deviceId;

    private String deviceFirmWareVersion;

    @Builder
    public Device(Long mdn, String terminalId, Integer makerId, Integer packetVersion,
        Integer deviceId, String deviceFirmWareVersion) {
        this.mdn = mdn;
        this.terminalId = terminalId;
        this.makerId = makerId;
        this.packetVersion = packetVersion;
        this.deviceId = deviceId;
        this.deviceFirmWareVersion = deviceFirmWareVersion;
    }
}
