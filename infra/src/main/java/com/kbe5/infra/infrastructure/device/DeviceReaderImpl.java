package com.kbe5.infra.infrastructure.device;

import com.kbe5.domain.device.entity.Device;
import com.kbe5.domain.device.repository.DeviceRepository;
import com.kbe5.domain.device.service.DeviceReader;
import com.kbe5.domain.exception.DeviceException;
import com.kbe5.domain.exception.DeviceResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeviceReaderImpl implements DeviceReader {

    private final DeviceRepository deviceRepository;

    public boolean existsByMdn(Long mdn){
        return deviceRepository.findByMdn(mdn).isPresent();
    }

    @Override
    public Device findByMdn(Long mdn) {
        return deviceRepository.findByMdn(mdn).orElseThrow(() ->
            new DeviceException(DeviceResultCode.MISMATCHED_MDN)
        );
    }

}
