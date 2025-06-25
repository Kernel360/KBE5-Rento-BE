package com.kbe5.api.domain.statistics.controller;

import com.kbe5.api.domain.statistics.dto.MonthlyStatsResponse;
import com.kbe5.common.response.api.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface MonthlyController {
    ResponseEntity<ApiResponse<MonthlyStatsResponse>> getStats(String companyCode, int year, int month);
}
