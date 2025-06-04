package com.kbe5.rento.domain.event.service;

import com.kbe5.rento.domain.device.entity.DeviceToken;
import com.kbe5.rento.domain.event.entity.CycleEvent;
import com.kbe5.rento.domain.event.entity.OnOffEvent;
import com.kbe5.rento.domain.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    @Transactional
    public OnOffEvent ignitionOnEvent(OnOffEvent onEvent, DeviceToken deviceToken) {

        onEvent.validateMdnMatch(deviceToken.getMdn());

        //todo: db 에서 던져주는 runtime error 어떻게 처리할지 생각해 봐야합니다
        return eventRepository.save(onEvent);
    }


    @Transactional
    public OnOffEvent iginitionOffEvent(OnOffEvent offEvent, DeviceToken deviceToken) {

        offEvent.validateMdnMatch(deviceToken.getMdn());

        //todo: db 에서 던져주는 runtime error 어떻게 처리할지 생각해 봐야합니다
        return eventRepository.save(offEvent);
    }

    @Transactional
    public CycleEvent saveCycleEvent(CycleEvent cycleEvent) {

        return eventRepository.save(cycleEvent);
    }
}
