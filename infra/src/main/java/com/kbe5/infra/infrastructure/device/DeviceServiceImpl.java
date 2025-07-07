package com.kbe5.infra.infrastructure.device;

import com.kbe5.domain.device.dto.DeviceCommand;
import com.kbe5.domain.device.dto.DeviceInfo;
import com.kbe5.domain.device.entity.Device;
import com.kbe5.domain.device.entity.DeviceToken;
import com.kbe5.domain.device.service.DeviceReader;
import com.kbe5.domain.device.service.DeviceService;
import com.kbe5.domain.device.service.DeviceStore;
import com.kbe5.domain.exception.DeviceException;
import com.kbe5.domain.exception.DeviceResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceStore deviceStore;
    private final DeviceReader deviceReader;

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
        return null;
    }

    @Override
    public DeviceToken validateAndGetToken(String token) {
        return null;
    }

    @Override
    public void deleteToken(String token) {

    }
}
