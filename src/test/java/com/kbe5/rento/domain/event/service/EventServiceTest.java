package com.kbe5.rento.domain.event.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kbe5.rento.common.datetime.DateUtil;
import com.kbe5.rento.domain.device.entity.DeviceToken;
import com.kbe5.rento.domain.event.dto.request.onoff.OnEventRequest;
import com.kbe5.rento.domain.device.enums.GpsCondition;
import com.kbe5.rento.domain.event.entity.OnOffEvent;
import com.kbe5.rento.domain.event.repository.EventRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    private static final Long EXPIRED_MS = 60 * 60 * 1000L;

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    @Test
    @DisplayName("ignitionOnEvent 정상 동작")
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

        Long tokenMdn = 12345678901L;
        DeviceToken deviceToken = mock(DeviceToken.class);
        when(deviceToken.getMdn()).thenReturn(tokenMdn);

        Long deviceUniqueId = 1L;
        OnOffEvent onOffEvent = request.toEntity(deviceUniqueId);
        when(eventRepository.save(any(OnOffEvent.class))).thenReturn(onOffEvent);

        OnOffEvent result = eventService.ignitionOnEvent(onOffEvent, deviceToken);
//
//        //저장테스트
        verify(eventRepository).save(any(OnOffEvent.class));
//
//        //response 변환테스트
        assertThat(result.getMdn())
            .usingRecursiveComparison()
            .isEqualTo(12345678901L);
    }

    @Test
    @DisplayName("토큰과 리퀘스트의 mdn 이 다르면 실패")
    void ignitionOnEvent_validateMdnMatchFail() {
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


        Long tokenMdn = 99999999999L; // 일부러 다르게!

        DeviceToken deviceToken = mock(DeviceToken.class);
        when(deviceToken.getMdn()).thenReturn(tokenMdn);

        Long deviceUniqueId = 1L;
        OnOffEvent onOffEvent = request.toEntity(deviceUniqueId);

        // when & then
        assertThatThrownBy(() -> eventService.ignitionOnEvent(onOffEvent, deviceToken))
            .isInstanceOf(com.kbe5.rento.common.exception.DeviceException.class);
    }

}
