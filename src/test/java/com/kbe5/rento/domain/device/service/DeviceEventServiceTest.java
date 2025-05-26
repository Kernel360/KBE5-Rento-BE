package com.kbe5.rento.domain.device.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kbe5.rento.common.datetime.DateUtil;
import com.kbe5.rento.domain.device.dto.request.OnEventRequest;
import com.kbe5.rento.domain.device.dto.resonse.OnEventResponse;
import com.kbe5.rento.domain.device.entity.event.OnOffEvent;
import com.kbe5.rento.domain.device.enums.GpsCondition;
import com.kbe5.rento.domain.device.repository.DeviceEventRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeviceEventServiceTest {

    @InjectMocks
    private DeviceEventService deviceEventService;

    @Mock
    private DeviceEventRepository deviceEventRepository;

    @Test
    @DisplayName("ignitionOnEvent 정상 동작 테스트")
    void ignitionOnEvent_success() {
        OnEventRequest request = new OnEventRequest(
            12345678901L,
            "A001",
            6,
            5,
            1,
            DateUtil.toOnOffEventLocalDateTime("20240523120000"),
            DateUtil.toOnOffEventLocalDateTime(""),
            GpsCondition.NORMAL,
            new BigDecimal("37.123456"),
            new BigDecimal("127.123456"),
            90,
            50,
            123456L
        );

        OnOffEvent event = OnOffEvent.from(request);

        when(deviceEventRepository.save(any(OnOffEvent.class))).thenReturn(event);

        OnEventResponse result = deviceEventService.ignitionOnEvent(request);

        //저장테스트
        verify(deviceEventRepository).save(any(OnOffEvent.class));

        //response 변환테스트
        assertThat(result.mobileDeviceNumber())
            .usingRecursiveComparison()
            .isEqualTo(12345678901L);
    }

}
