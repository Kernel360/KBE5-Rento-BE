package com.kbe5.api.domain.device.dto.resonse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kbe5.common.exception.DeviceResultCode;
import com.kbe5.common.util.LocalDateTimeSerializer;


import java.time.LocalDateTime;
import java.util.List;

public record DeviceSettingResponse(
        @JsonProperty("rstCd")
        String resultCode,

        @JsonProperty("rstMsg")
        String resultMessage,

        @JsonProperty("mdn")
        Long mdn,

        @JsonProperty("oTime")
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        LocalDateTime oTime,

        @JsonProperty("ctrCnt")
        int controlCount, // 제어명령 개수

        @JsonProperty("geoCnt")
        int geofenceCount,

        @JsonProperty("crtList")
        List<DeviceControlInfoResponse> deviceControlInfoResponseList,

        @JsonProperty("geoList")
        List<GeofenceControlInfoResponse> geofenceControlInfoResponseList
) {
        public static DeviceSettingResponse of(DeviceResultCode resultCode, long mdn, LocalDateTime oTime
                , int controlCount, int geofenceCount, List<DeviceControlInfoResponse> deviceControlInfoResponseList,
                                               List<GeofenceControlInfoResponse> geofenceControlInfoResponseList) {
                return new DeviceSettingResponse(resultCode.getCode(), resultCode.getMessage(), mdn, oTime, controlCount, geofenceCount,
                        deviceControlInfoResponseList, geofenceControlInfoResponseList);
        }
}
