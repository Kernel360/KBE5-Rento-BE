package com.kbe5.rento.domain.event.service;

import com.kbe5.rento.domain.event.entity.CycleEvent;
import com.kbe5.rento.domain.event.entity.CycleInfo;
import com.kbe5.rento.domain.event.repository.CycleInfoRepository;
import com.kbe5.rento.domain.event.repository.EventRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CycleInfoService {

    private final CycleInfoRepository cycleInfoRepository;

    private final EventRepository eventRepository;

    @Transactional
    public CycleEvent saveCycleEvent(CycleEvent cycleEvent) {

        return eventRepository.save(cycleEvent);
    }

    @Transactional
    public void saveCycleInfo(List<CycleInfo> cycleInfo) {
       cycleInfoRepository.saveAll(cycleInfo);
    }
}
