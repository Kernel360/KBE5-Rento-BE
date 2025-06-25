package com.kbe5.api.domain.statistics.controller;

import com.kbe5.api.domain.statistics.dto.MonthlyStatsResponse;
import com.kbe5.api.domain.statistics.service.MonthlyService;
import com.kbe5.common.apiresponse.ResEntityFactory;
import com.kbe5.common.response.api.ApiResponse;
import com.kbe5.common.response.api.ApiResultCode;
import com.kbe5.domain.statistics.entity.MonthlyStats;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/monthly")
public class MonthlyControllerImpl implements MonthlyController {
    private final MonthlyService monthlyService;

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<MonthlyStatsResponse>> getStats(
            @RequestParam String companyCode,
            @RequestParam int year,
            @RequestParam int month) {

        return monthlyService.getStats(companyCode, year, month)
                .map(stats -> ResEntityFactory.toResponse(ApiResultCode.SUCCESS, MonthlyStatsResponse.fromEntity(stats)))
                .orElse(ResponseEntity.noContent().build()); // 204 No Content
    }
}
