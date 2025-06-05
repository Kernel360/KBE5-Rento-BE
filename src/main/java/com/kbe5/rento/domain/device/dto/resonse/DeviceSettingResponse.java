package com.kbe5.rento.domain.device.dto.resonse;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.time.LocalDateTime;

public record DeviceSettingResponse(
        @JsonProperty("rstCd")
        String resultCode,

        @JsonProperty("rstMsg")
        String resultMessage,

        @JsonProperty("mdn")
        Long mdn,

        @JsonProperty("oTime")
        LocalDateTime oTime,

        @JsonProperty("ctrCnt")
        String controlCount, // 제어명령 개수

        @JsonProperty("geoCnt")
        int geofenceCount,

        @JsonProperty("crtList")
        List<DeviceRegisterResponse> deviceRegisterResponseList,

        @JsonProperty("geoList")
        List<GeofenceControlInfoResponse> geofenceControlInfoResponseList
) {
}
