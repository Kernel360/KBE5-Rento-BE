package com.kbe5.api.domain.device.mapper;

import com.kbe5.api.domain.device.dto.resonse.DeviceControlInfoResponse;
import com.kbe5.api.domain.device.dto.resonse.DeviceRegisterResponse;
import com.kbe5.api.domain.device.dto.resonse.DeviceSettingResponse;
import com.kbe5.api.domain.device.dto.resonse.DeviceTokenDeleteResponse;
import com.kbe5.api.domain.device.dto.resonse.DeviceTokenResponse;
import com.kbe5.api.domain.device.dto.resonse.GeofenceControlInfoResponse;
import com.kbe5.domain.device.dto.DeviceInfo;
import com.kbe5.domain.exception.DeviceResultCode;
import java.time.LocalDateTime;
import java.util.List;

public class DeviceResponseMapper {

    /**
     * public static CompanyRegisterResponse toRegisterResponse(CompanyInfo info) { return new CompanyRegisterResponse(
     * info.getName(), info.getCompanyCode() ); }
     */
    public static DeviceRegisterResponse toRegisterResponse(DeviceInfo.Register info, DeviceResultCode resultCode) {
        return new DeviceRegisterResponse(
            resultCode.getCode(), resultCode.getMessage(), info.getMdn()
        );
    }

    public static DeviceTokenResponse toIssueTokenResponse(DeviceInfo.IssueToken info, DeviceResultCode resultCode) {
        return new DeviceTokenResponse(resultCode.getCode(), resultCode.getMessage(), info.getMdn(), info.getToken(),
            info.getExPeriod());
    }

    public static DeviceTokenDeleteResponse toTokenDeleteResponse(
        DeviceInfo.DeleteToken info, DeviceResultCode resultCode) {
        return new DeviceTokenDeleteResponse(resultCode.getCode(), resultCode.getMessage(), info.getToken());
    }

    public static DeviceSettingResponse toDeviceSettingResponse(
        DeviceInfo.DeviceSettings info, Long mdn, DeviceResultCode resultCode) {
        List<DeviceControlInfoResponse> deviceControlResponses = info.getDeviceControlInfos().stream()
            .map(DeviceResponseMapper::toDeviceControlResponse)
            .toList();

        List<GeofenceControlInfoResponse> geofenceControlResponses = info.getGeofenceControlInfos().stream()
            .map(DeviceResponseMapper::toGeofenceControlResponse)
            .toList();

        return new DeviceSettingResponse(
            resultCode.getCode(),
            resultCode.getMessage(),
            mdn,
            LocalDateTime.now(),
            deviceControlResponses.size(),
            geofenceControlResponses.size(),
            deviceControlResponses,
            geofenceControlResponses);
    }

    public static DeviceControlInfoResponse toDeviceControlResponse(
        DeviceInfo.DeviceControl deviceControl
    ) {
        return new DeviceControlInfoResponse(
            deviceControl.getId(), deviceControl.getCtrCd(), deviceControl.getCtrVal());
    }

    public static GeofenceControlInfoResponse toGeofenceControlResponse(
        DeviceInfo.GeofenceControl geofenceControl
    ) {
        return new GeofenceControlInfoResponse(
            geofenceControl.getGeoCtrId(),
            geofenceControl.getUpVal(),
            geofenceControl.getGeoGrpId(),
            geofenceControl.getGeoEvtTp(),
            geofenceControl.getGeoRange(),
            geofenceControl.getLat(),
            geofenceControl.getLon(),
            geofenceControl.getOnTime(),
            geofenceControl.getOffTime(),
            geofenceControl.getStoreTp()
        );
    }
}
