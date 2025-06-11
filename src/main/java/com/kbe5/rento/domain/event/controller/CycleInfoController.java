package com.kbe5.rento.domain.event.controller;

import com.kbe5.rento.domain.event.dto.response.EventDataResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CycleInfoController {

    ResponseEntity<List<EventDataResponse>> getList();

}
