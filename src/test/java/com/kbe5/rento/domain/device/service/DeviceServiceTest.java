package com.kbe5.rento.domain.device.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kbe5.rento.domain.device.dto.request.DeviceRegisterRequest;
import com.kbe5.rento.domain.device.entity.Device;
import com.kbe5.rento.common.exception.DeviceException;
import com.kbe5.rento.domain.device.repository.DeviceRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {

    @InjectMocks
    private DeviceService deviceService;

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private JwtUtil jwtUtil;

    private DeviceRegisterRequest request;

    private Device testDevice;

    @BeforeEach
    void before() {
        //given
        request = new DeviceRegisterRequest(
            1L,        // mdn
            "A001",    // terminalId
            6,         // makerId
            5,         // packetVersion
            1          // deviceId
        );

        testDevice = Device.builder()
            .mdn(request.mdn())
            .terminalId(request.terminalId())
            .makerId(request.makerId())
            .packetVersion(request.packetVersion())
            .deviceId(request.deviceId())
            .deviceFirmWareVersion("LTE 1.2")
            .build();

    }
    @Test
    @DisplayName("디바이스 등록 테스트 입니다")
    void registerDevice() {

        //stub
        when(deviceRepository.findByMdn(request.mdn()))
            .thenReturn(Optional.empty());

        // when
        Device device = request.toDevice();

        deviceService.registerDevice(device);

        // then
        verify(deviceRepository).save(any(Device.class));
    }

    @Test
    @DisplayName("디바이스 등록 중복 체크 실패")
    void registerDeviceFail() {

        //stub
        when(deviceRepository.findByMdn(request.mdn()))
            .thenReturn(Optional.of(mock(Device.class)));

        //when
        Device device = request.toDevice();

        assertThatThrownBy(() -> deviceService.registerDevice(device))
            .isInstanceOf(DeviceException.class);

        //then
        verify(deviceRepository, never()).save(any(Device.class));
    }

}
