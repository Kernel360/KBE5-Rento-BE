package com.kbe5.api.domain.cycleinfosummary.controller;


import com.kbe5.api.domain.cycleinfosummary.dto.CycleInfoSummaryResponse;
import com.kbe5.api.domain.cycleinfosummary.service.CycleInfoSummaryService;
import com.kbe5.common.apiresponse.ResEntityFactory;
import com.kbe5.common.response.api.ApiResponse;
import com.kbe5.common.response.api.ApiResultCode;
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
public class CycleInfoSummaryControllerImpl implements CycleInfoController{

    private final CycleInfoSummaryService cycleInfoSummaryService;

    @GetMapping("/{driveId}")
    public ResponseEntity<ApiResponse<List<CycleInfoSummaryResponse>>> getList(@PathVariable Long driveId){
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, cycleInfoSummaryService.getList(driveId));
    }
}
