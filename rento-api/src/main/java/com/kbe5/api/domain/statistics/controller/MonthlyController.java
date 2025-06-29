package com.kbe5.api.domain.statistics.controller;

import com.kbe5.api.domain.statistics.dto.MonthlyStatsResponse;
import com.kbe5.common.response.api.ApiResponse;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;

@Hidden
public interface MonthlyController {
    ResponseEntity<ApiResponse<MonthlyStatsResponse>> getStats(String companyCode, int year, int month);
}
