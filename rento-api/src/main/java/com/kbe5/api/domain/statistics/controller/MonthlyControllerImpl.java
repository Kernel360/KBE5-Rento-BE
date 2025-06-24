package com.kbe5.api.domain.statistics.controller;

import com.kbe5.api.domain.statistics.dto.MonthlyStatsRequest;
import com.kbe5.api.domain.statistics.dto.MonthlyStatsResponse;
import com.kbe5.api.domain.statistics.service.MonthlyService;
import com.kbe5.common.apiresponse.ResEntityFactory;
import com.kbe5.common.response.api.ApiResponse;
import com.kbe5.common.response.api.ApiResultCode;
import com.kbe5.domain.statistics.repository.MonthlyStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/monthly")
public class MonthlyControllerImpl {
    private final MonthlyService monthlyService;

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<MonthlyStatsResponse>> getStats(@RequestBody MonthlyStatsRequest request) {

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS,
                MonthlyStatsResponse.fromEntity(monthlyService.getStats(request)));

    }
}
