package com.kbe5.domain.device.service;

import com.kbe5.domain.device.dto.DeviceCommand;
import com.kbe5.domain.device.dto.DeviceInfo;
import com.kbe5.domain.device.entity.DeviceToken;

public interface DeviceService {

    /**
     * todo:
     *  1. 디바이스 등록 (ok)
     *  2. 디바이스 삭제 (물리)
     *  3. 디바이스 업데이트
     *  4. 토큰 발급 (ok)
     *  5. 토큰 삭제 (ok)
     *  6. 토큰 갱신
     */

    DeviceInfo.Register registerDevice(DeviceCommand.Register command);

    DeviceInfo.DeleteDevice deleteDevice(DeviceCommand.DeleteDevice command);

    DeviceInfo.DeviceSettings getDeviceSetInfo(Long mdn);

    DeviceInfo.IssueToken issueToken(Long mdn);

    DeviceInfo.DeleteToken deleteToken(String token);

    DeviceToken findDeviceToken(String token);
}
