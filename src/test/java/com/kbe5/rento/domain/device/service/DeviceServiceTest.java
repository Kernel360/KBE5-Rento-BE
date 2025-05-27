package com.kbe5.rento.domain.device.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kbe5.rento.domain.device.dto.request.DeviceRegisterRequest;
import com.kbe5.rento.domain.device.entity.Device;
import com.kbe5.rento.domain.device.repository.DeviceRepository;
import java.util.Optional;
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

    @Test
    @DisplayName("디바이스 등록 테스트 입니다")
    void registerDevice() {

        //given
        DeviceRegisterRequest deviceRegisterRequest = new DeviceRegisterRequest(
            1L,
            "A001",
            6,
            5,
            1
        );

        //stub
        when(deviceRepository.findByMobileDeviceNumber(deviceRegisterRequest.mobileDeviceNumber()))
            .thenReturn(Optional.empty());

        // when
        deviceService.registerDevice(deviceRegisterRequest);

        // then
        verify(deviceRepository).save(any(Device.class));
    }

    @Test
    @DisplayName("디바이스 등록 중복 체크 실패")
    void registerDeviceFail() {

        //given
        DeviceRegisterRequest deviceRegisterRequest = new DeviceRegisterRequest(
            1L,
            "A001",
            6,
            5,
            1
        );

        //stub
        when(deviceRepository.findByMobileDeviceNumber(deviceRegisterRequest.mobileDeviceNumber()))
            .thenReturn(Optional.of(mock(Device.class)));

        //when
        assertThatThrownBy(() -> deviceService.registerDevice(deviceRegisterRequest))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이미 등록된 디바이스입니다.");

        //then
        verify(deviceRepository, never()).save(any(Device.class));
    }
}
