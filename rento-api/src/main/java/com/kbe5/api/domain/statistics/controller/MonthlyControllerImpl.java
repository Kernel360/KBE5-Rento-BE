package com.kbe5.api.domain.statistics.controller;

import com.kbe5.api.domain.statistics.dto.MonthlyStatsResponse;
import com.kbe5.api.domain.statistics.mapper.MonthlyStatResponseMapper;
import com.kbe5.common.apiresponse.ResEntityFactory;
import com.kbe5.common.response.api.ApiResponse;
import com.kbe5.common.response.api.ApiResultCode;
import com.kbe5.domain.statistics.service.MonthlyStatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/monthly")
public class MonthlyControllerImpl implements MonthlyController {
    private final MonthlyStatService monthlyStatService;
    private final MonthlyStatResponseMapper monthlyStatResponseMapper;

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<MonthlyStatsResponse>> getStats(
            @RequestParam String companyCode,
            @RequestParam int year,
            @RequestParam int month) {

        return monthlyStatService.getStats(companyCode, year, month)
                .map(stats -> ResEntityFactory.toResponse(ApiResultCode.SUCCESS, monthlyStatResponseMapper.toResponse(stats)))
                .orElse(ResponseEntity.noContent().build()); // 204 No Content
    }
}
