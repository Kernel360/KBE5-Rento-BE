package com.kbe5.domain.device.dto;

import com.kbe5.domain.device.entity.Device;
import com.kbe5.domain.device.entity.DeviceControlInfo;
import com.kbe5.domain.device.entity.DeviceToken;
import com.kbe5.domain.device.entity.GeofenceControlInfo;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class DeviceInfo {

    @Getter
    @AllArgsConstructor
    public static class Register{

        private Long id;
        private Long mdn;

        public static Register fromEntity(Device device) {
            return new Register(device.getId(), device.getMdn());
        }
    }

    @Getter
    @AllArgsConstructor
    public static class DeleteDevice{
        private Long id;
        private Long mdn;

        public static DeleteDevice fromEntity(Device device) {
            return new DeleteDevice(device.getId(), device.getMdn());
        }
    }

    @Getter
    @AllArgsConstructor
    public static class DeviceSettings{

        List<DeviceInfo.DeviceControl> deviceControlInfos;

        List<DeviceInfo.GeofenceControl> geofenceControlInfos;

        public static DeviceSettings of(
            List<DeviceInfo.DeviceControl> deviceControlInfos, List<DeviceInfo.GeofenceControl> geofenceControlInfos) {
            return new DeviceSettings(deviceControlInfos, geofenceControlInfos);
        }
    }

    @Getter
    @AllArgsConstructor
    public static class IssueToken {
        private Long mdn;
        private String token;
        private Long exPeriod;

        public static DeviceInfo.IssueToken fromEntity(DeviceToken deviceToken){
            return new DeviceInfo.IssueToken( deviceToken.getMdn(), deviceToken.getToken(),
                deviceToken.getExPeriod());
        }
    }

    @Getter
    @AllArgsConstructor
    public static class DeleteToken {
        private String token;

        public static DeviceInfo.DeleteToken fromEntity(DeviceToken deviceToken) {
            return new DeviceInfo.DeleteToken(deviceToken.getToken());
        }
    }

    @Getter
    @AllArgsConstructor
    public static class DeviceControl {
        private Long id;
        private Long mdn;

        private String ctrId;

        private String ctrCd;

        private String ctrVal;

        public static DeviceInfo.DeviceControl fromEntity(DeviceControlInfo deviceControlInfo){
            return new DeviceInfo.DeviceControl(
                deviceControlInfo.getId(),
                deviceControlInfo.getMdn(),
                deviceControlInfo.getCtrId(),
                deviceControlInfo.getCtrCd(),
                deviceControlInfo.getCtrId());
        }
    }

    @Getter
    @AllArgsConstructor
    public static class GeofenceControl {
        private Long mdn;
        private Long geoCtrId;
        private Integer upVal;
        private Integer geoGrpId;
        private Short geoEvtTp;
        private Double geoRange;
        private BigDecimal lat;
        private BigDecimal lon;
        private LocalDateTime onTime;
        private LocalDateTime offTime;
        private Short storeTp;

        public static DeviceInfo.GeofenceControl fromEntity(GeofenceControlInfo geofenceControlInfo) {
            return new DeviceInfo.GeofenceControl(
                geofenceControlInfo.getMdn(),
                geofenceControlInfo.getGeoCtrId(),
                geofenceControlInfo.getUpVal(),
                geofenceControlInfo.getGeoGrpId(),
                geofenceControlInfo.getGeoEvtTp(),
                geofenceControlInfo.getGeoRange(),
                geofenceControlInfo.getLat(),
                geofenceControlInfo.getLon(),
                geofenceControlInfo.getOnTime(),
                geofenceControlInfo.getOffTime(),
                geofenceControlInfo.getStoreTp()
            );
        }
    }
}
