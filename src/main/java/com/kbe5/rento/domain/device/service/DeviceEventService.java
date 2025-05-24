package com.kbe5.rento.domain.device.service;

import com.kbe5.rento.domain.device.dto.request.OnEventRequest;
import com.kbe5.rento.domain.device.dto.resonse.OnEventResponse;
import com.kbe5.rento.domain.device.entity.event.OnOffEvent;
import com.kbe5.rento.domain.device.repository.DeviceEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeviceEventService {

    private final DeviceEventRepository deviceEventRepository;

    @Transactional
    public OnEventResponse ignitionOnEvent(OnEventRequest request) {

        //토큰 기반으로 단말기 인증과 만료기간 기능 추가해야합니다
        return OnEventResponse.from(deviceEventRepository.save(OnOffEvent.from(request)));
    }

}
