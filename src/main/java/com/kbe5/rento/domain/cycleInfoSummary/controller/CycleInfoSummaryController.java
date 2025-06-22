package com.kbe5.rento.domain.cycleInfoSummary.controller;

import com.kbe5.rento.common.apiresponse.ResEntityFactory;
import com.kbe5.rento.common.response.api.ApiResponse;
import com.kbe5.rento.common.response.api.ApiResultCode;
import com.kbe5.rento.domain.cycleInfoSummary.dto.CycleInfoSummaryResponse;
import com.kbe5.rento.domain.cycleInfoSummary.service.CycleInfoSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cycleInfoSummary")
public class CycleInfoSummaryController {

    private final CycleInfoSummaryService cycleInfoSummaryService;

    @GetMapping("/{driveId}")
    public ResponseEntity<ApiResponse<List<CycleInfoSummaryResponse>>> getList(@PathVariable Long driveId){
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, cycleInfoSummaryService.getList(driveId));
    }
}
