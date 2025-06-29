package com.kbe5.api.domain.cycleinfosummary.controller;

import com.kbe5.api.domain.cycleinfosummary.dto.CycleInfoSummaryResponse;
import com.kbe5.common.response.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "운행상세 지도 표시 서비스 API")
public interface CycleInfoController {

    @Operation(summary = "운행상세 지도 조회")
    @Parameter(name = "driveId", description = "운행 상세 때 보여줄 운행ID", example = "1", required = true)
    ResponseEntity<ApiResponse<List<CycleInfoSummaryResponse>>> getList(Long driveId);
}
