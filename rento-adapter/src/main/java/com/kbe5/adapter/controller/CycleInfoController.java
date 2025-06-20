package com.kbe5.adapter.controller;

import com.kbe5.adapter.dto.response.EventDataResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CycleInfoController {

    ResponseEntity<List<EventDataResponse>> getList();

}
