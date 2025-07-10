package com.kbe5.api.domain.cycleDatasummary.controller;


import com.kbe5.api.domain.cycleDatasummary.dto.CycleDataSummaryResponse;
import com.kbe5.api.domain.cycleDatasummary.mapper.CycleDataSummaryResponseMapper;
import com.kbe5.common.apiresponse.ResEntityFactory;
import com.kbe5.common.response.api.ApiResponse;
import com.kbe5.common.response.api.ApiResultCode;
import com.kbe5.domain.cycleinfosummary.service.CycleDataSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cycleInfoSummary")
public class CycleDataSummaryControllerImpl implements CycleDataSummaryController {

    private final CycleDataSummaryService cycleDataSummaryService;
    private final CycleDataSummaryResponseMapper cycleDataSummaryResponseMapper;

    @GetMapping("/{driveId}")
    public ResponseEntity<ApiResponse<List<CycleDataSummaryResponse>>> getList(@PathVariable Long driveId){
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS,
                cycleDataSummaryService.getList(driveId)
                        .stream()
                        .map(cycleDataSummaryResponseMapper::toCycleInfoSummary)
                        .toList());
    }
}
