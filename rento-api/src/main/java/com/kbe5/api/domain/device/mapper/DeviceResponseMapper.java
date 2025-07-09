package com.kbe5.api.domain.device.mapper;

import com.kbe5.api.domain.device.dto.resonse.DeviceRegisterResponse;
import com.kbe5.api.domain.device.dto.resonse.DeviceTokenDeleteResponse;
import com.kbe5.api.domain.device.dto.resonse.DeviceTokenResponse;
import com.kbe5.domain.device.dto.DeviceInfo;
import com.kbe5.domain.exception.DeviceResultCode;

public class DeviceResponseMapper {

    /**
     * public static CompanyRegisterResponse toRegisterResponse(CompanyInfo info) { return new CompanyRegisterResponse(
     * info.getName(), info.getCompanyCode() ); }
     */
    public static DeviceRegisterResponse toRegisterResponse(
        DeviceInfo.Register info, DeviceResultCode resultCode) {
        return new DeviceRegisterResponse(
            resultCode.getCode(), resultCode.getMessage(), info.getMdn()
        );
    }

    public static DeviceTokenResponse toIssueTokenResponse(
        DeviceInfo.IssueToken info, DeviceResultCode resultCode
    ) {
        return new DeviceTokenResponse(resultCode.getCode(), resultCode.getMessage(), info.getMdn(), info.getToken(),
            info.getExPeriod());
    }

    public static DeviceTokenDeleteResponse toTokenDeleteResponse(
        DeviceInfo.DeleteToken info, DeviceResultCode resultCode
    ) {
        return new DeviceTokenDeleteResponse(resultCode.getCode(), resultCode.getMessage(), info.getToken());
    }
}
