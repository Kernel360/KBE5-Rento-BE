package com.kbe5.domain.device.service;

import com.kbe5.domain.device.dto.DeviceCommand;
import com.kbe5.domain.device.dto.DeviceInfo;
import com.kbe5.domain.device.dto.DeviceInfo.DeleteDevice;
import com.kbe5.domain.device.dto.DeviceInfo.DeleteToken;
import com.kbe5.domain.device.dto.DeviceInfo.DeviceControl;
import com.kbe5.domain.device.dto.DeviceInfo.DeviceSettings;
import com.kbe5.domain.device.dto.DeviceInfo.GeofenceControl;
import com.kbe5.domain.device.entity.Device;
import com.kbe5.domain.device.entity.DeviceControlInfo;
import com.kbe5.domain.device.entity.DeviceToken;
import com.kbe5.domain.device.entity.GeofenceControlInfo;
import com.kbe5.domain.drive.service.DriveService;
import com.kbe5.domain.exception.DeviceException;
import com.kbe5.domain.exception.DeviceResultCode;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private static final Long EXPIRED_MS = 4 * 60 * 60 * 1000L;

    private final DeviceStore deviceStore;
    private final DeviceReader deviceReader;
    private final DeviceTokenStore deviceTokenStore;
    private final DriveService driveService;

    @Override
    @Transactional
    public DeviceInfo.Register registerDevice(DeviceCommand.Register command) {
        //중복확인
        Device device = command.toEntity();

        deviceReader.validateDuplicateDevice(device.getMdn());
        Device registerdDevice = deviceStore.store(device);

        return DeviceInfo.Register.fromEntity(registerdDevice);
    }

    @Override
    public DeleteDevice deleteDevice(DeviceCommand.DeleteDevice command) {
        return null;
    }

    @Override
    public DeviceInfo.DeviceSettings getDeviceSetInfo(Long mdn) {

        //RESPONSE 로 만들어야할까?
        List<DeviceControlInfo> deviceControlInfos = deviceReader.findAllDeviceControlInfoByMdn(mdn);
        List<GeofenceControlInfo> geofenceControlInfos = deviceReader.findAllGeofenceControlInfoByMdn(mdn);

        if (isControlInfosEmpty(deviceControlInfos, geofenceControlInfos)) {
            throw new DeviceException(DeviceResultCode.NO_SEARCH_RESULTS);
        }

        //DeviceInfo로 변환
        List<DeviceControl> deviceControls = deviceControlInfos.stream().map(DeviceControl::fromEntity).toList();
        List<GeofenceControl> geofenceControls = geofenceControlInfos.stream().map(GeofenceControl::fromEntity).toList();

        return DeviceInfo.DeviceSettings.of(deviceControls, geofenceControls);
    }

    private boolean isControlInfosEmpty(
        List<DeviceControlInfo> deviceControlInfos, List<GeofenceControlInfo> geofenceControlInfos) {
        return deviceControlInfos.isEmpty() || geofenceControlInfos.isEmpty();
    }

    @Override
    @Transactional
    public DeviceInfo.IssueToken issueToken(Long mdn) {
        // 1.mdn으로 가져온다
        Device device = deviceReader.findByMdn(mdn);

        // todo: 어떻게 수정해야하는가? -> 최종 코드는 그냥 LocalDate.now( )로 하면 서비스 흐름이 맞을듯 6.18
        Long driveId = driveService.findDriveForEvent(device.getMdn(), LocalDateTime.now());

        DeviceToken token = device.issueToken(EXPIRED_MS, driveId);
        DeviceToken storedDeviceToken = deviceTokenStore.store(token);

        return DeviceInfo.IssueToken.fromEntity(storedDeviceToken);
    }

    @Override
    @Transactional
    public DeviceInfo.DeleteToken deleteToken(String token) {
        DeviceToken deletedToken = deviceTokenStore.delete(token);
        return DeleteToken.fromEntity(deletedToken);
    }
}
