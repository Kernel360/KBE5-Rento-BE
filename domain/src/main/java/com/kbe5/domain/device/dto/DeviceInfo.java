package com.kbe5.domain.device.dto;

import com.kbe5.domain.device.entity.Device;
import com.kbe5.domain.device.entity.DeviceToken;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class DeviceInfo {

    @Getter
    @AllArgsConstructor
    public static class Register{

        private Long id;
        private Long mdn;

        public static DeviceInfo.Register fromEntity(Device device) {
            return new DeviceInfo.Register(device.getId(), device.getMdn());
        }
    }


    @Getter
    @AllArgsConstructor
    public static class DeleteDevice{
        private Long id;
        private Long mdn;

        public static DeviceInfo.DeleteDevice fromEntity(Device device) {
            return new DeviceInfo.DeleteDevice(device.getId(), device.getMdn());
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

    public class DeviceSettings {

    }

    @Getter
    @AllArgsConstructor
    public static class DeleteToken {
        private String token;

        public static DeviceInfo.DeleteToken fromEntity(DeviceToken deviceToken) {
            return new DeviceInfo.DeleteToken(deviceToken.getToken());
        }
    }
}
