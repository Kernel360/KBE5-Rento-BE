package com.kbe5.rento.domain.device.dto.resonse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kbe5.rento.common.datetime.EventLocalDateTimeDeserializer;
import com.kbe5.rento.common.datetime.LocalDateTimeSerializer;
import com.kbe5.rento.domain.device.enums.DeviceResultCode;

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
