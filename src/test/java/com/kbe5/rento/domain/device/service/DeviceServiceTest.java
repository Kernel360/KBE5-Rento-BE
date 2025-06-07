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
import com.kbe5.rento.domain.device.entity.DeviceToken;
import com.kbe5.rento.domain.device.repository.DeviceRepository;
import com.kbe5.rento.domain.device.repository.DeviceTokenRepository;
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

    private static final Long EXPIRED_MS = 60 * 60 * 1000L;

    @InjectMocks
    private DeviceService deviceService;

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private DeviceTokenRepository deviceTokenRepository;

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
            1,          // deviceId
            "C1"
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
        Device device = request.toEntity();

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
        Device device = request.toEntity();

        assertThatThrownBy(() -> deviceService.registerDevice(device))
            .isInstanceOf(DeviceException.class);

        //then
        verify(deviceRepository, never()).save(any(Device.class));
    }

    @Test
    @DisplayName("정상적으로 토큰을 발급받는다")
    void issueToken_success() {
        // given
        when(deviceRepository.findByMdn(1L)).thenReturn(Optional.of(testDevice));
        when(deviceTokenRepository.save(any(DeviceToken.class))).thenAnswer(inv -> inv.getArgument(0));

        // when
        DeviceToken issued = deviceService.issueToken(1L);

        // then
        assertThat(issued.getMdn()).isEqualTo(1L);
        assertThat(issued.getToken()).isNotBlank();
    }

    @Test
    @DisplayName("MDN이 존재하지 않으면 예외 발생")
    void issueToken_mdnNotFound() {
        // given
        when(deviceRepository.findByMdn(12345L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> deviceService.issueToken(12345L))
            .isInstanceOf(DeviceException.class);
    }

    @Test
    @DisplayName("토큰이 유효하면 정상 반환")
    void validateAndGetToken_success() {
        // given
        DeviceToken deviceToken = DeviceToken.of(
            "test-token", testDevice, System.currentTimeMillis(), EXPIRED_MS// 1시간
        );
        when(deviceTokenRepository.findByToken("test-token")).thenReturn(Optional.of(deviceToken));

        // when
        DeviceToken found = deviceService.validateAndGetToken("test-token");

        // then
        assertThat(found).isEqualTo(deviceToken);
    }

    @Test
    @DisplayName("토큰이 없으면 예외 발생")
    void validateAndGetToken_tokenNotFound() {
        // given
        when(deviceTokenRepository.findByToken("notfound")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> deviceService.validateAndGetToken("notfound"))
            .isInstanceOf(DeviceException.class);
    }

    @Test
    @DisplayName("토큰이 만료되었으면 예외 발생")
    void validateAndGetToken_expired() {
        // given
        DeviceToken expiredToken = DeviceToken.of(
            "expired-token", testDevice, System.currentTimeMillis() - EXPIRED_MS - EXPIRED_MS
            , EXPIRED_MS// 1시간
        );
        when(deviceTokenRepository.findByToken("expired-token")).thenReturn(Optional.of(expiredToken));

        assertThatThrownBy(() -> deviceService.validateAndGetToken("expired-token"))
            .isInstanceOf(DeviceException.class);
    }

}
