package com.kbe5.infra.infrastructure.device;

import com.kbe5.domain.device.dto.DeviceCommand;
import com.kbe5.domain.device.dto.DeviceInfo;
import com.kbe5.domain.device.entity.Device;
import com.kbe5.domain.device.entity.DeviceToken;
import com.kbe5.domain.device.service.DeviceReader;
import com.kbe5.domain.device.service.DeviceService;
import com.kbe5.domain.device.service.DeviceStore;
import com.kbe5.domain.device.service.DeviceTokenStore;
import com.kbe5.domain.drive.service.DriveService;
import com.kbe5.domain.exception.DeviceException;
import com.kbe5.domain.exception.DeviceResultCode;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private static final Long EXPIRED_MS = 4 * 60 * 60 * 1000L;

    private final DeviceStore deviceStore;
    private final DeviceReader deviceReader;
    private final DeviceTokenStore deviceTokenStore;
    private final DriveService driveService;
    @Override
    public DeviceInfo registerDevice(DeviceCommand.Register command) {
        //중복확인
        validateDuplicateDevice(command.getMdn());

        Device device = command.toEntity();
        Device registerdDevice = deviceStore.store(device);

        return DeviceInfo.fromEntity(registerdDevice);
    }

    private void validateDuplicateDevice(Long mdn) {
        if (deviceReader.existsByMdn(mdn)) {
            throw new DeviceException(DeviceResultCode.MISMATCHED_MDN);
        }
    }

    @Override
    public DeviceToken issueToken(Long mdn) {
        Device device = deviceReader.findByMdn(mdn);

        // todo: 어떻게 수정해야하는가? -> 최종 코드는 그냥 LocalDate.now( )로 하면 서비스 흐름이 맞을듯 6.18
        Long driveId = driveService.findDriveForEvent(mdn, LocalDateTime.now());

        DeviceToken token = device.issueToken(EXPIRED_MS, driveId);
        DeviceToken deviceToken = deviceTokenStore.store(token);
        return deviceToken;
    }

    @Override
    public DeviceToken validateAndGetToken(String token) {
        return null;
    }

    @Override
    public void deleteToken(String token) {

    }
}
