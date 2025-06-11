package com.kbe5.rento.domain.event.controller;

import com.kbe5.rento.domain.event.dto.response.EventDataResponse;
import com.kbe5.rento.domain.event.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events/cycle-info")
public class CycleInfoControllerImpl implements CycleInfoController{
    private final EventService eventService;

    @Override
    @GetMapping("/get-list")
    public ResponseEntity<List<EventDataResponse>> getList() {

        List<EventDataResponse> eventDataResponseList = eventService.getList()
                .stream().map(EventDataResponse::fromEntity).toList();

        return ResponseEntity.ok(eventDataResponseList);
    }
}
