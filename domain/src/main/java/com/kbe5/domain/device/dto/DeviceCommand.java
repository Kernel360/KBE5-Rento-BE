package com.kbe5.domain.device.dto;

import com.kbe5.domain.device.entity.Device;
import lombok.Builder;
import lombok.Getter;

public class DeviceCommand {

    @Getter
    @Builder
    public static class Register{
        Long mdn;

        String terminalId;

        Integer makerId;

        Integer packetVersion;

        Integer deviceId;

        public Device toEntity() {
            return Device.builder()
                .mdn(this.mdn)
                .terminalId(this.terminalId)
                .makerId(this.makerId)
                .packetVersion(this.packetVersion)
                .deviceId(this.deviceId)
                .build();
        }
    }

    @Getter
    @Builder
    public static class IssueToken{

    }
}
