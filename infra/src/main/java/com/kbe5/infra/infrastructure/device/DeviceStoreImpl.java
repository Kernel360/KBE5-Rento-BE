package com.kbe5.infra.infrastructure.device;

import com.kbe5.domain.device.entity.Device;
import com.kbe5.domain.device.service.DeviceStore;
import com.kbe5.infra.infrastructure.device.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeviceStoreImpl implements DeviceStore {

    private final DeviceRepository deviceRepository;

    @Override
    public Device store(Device device) {
        return deviceRepository.save(device);
    }

    @Override
    public void delete(Device device) {

    }
}
