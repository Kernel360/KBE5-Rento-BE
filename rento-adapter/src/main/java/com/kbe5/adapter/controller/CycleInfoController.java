package com.kbe5.adapter.controller;

import com.kbe5.adapter.dto.response.EventDataResponse;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Hidden
public interface CycleInfoController {

    ResponseEntity<List<EventDataResponse>> getList();

}
