package com.kbe5.adapter.controller;


import com.kbe5.adapter.dto.response.EventDataResponse;
import com.kbe5.adapter.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
