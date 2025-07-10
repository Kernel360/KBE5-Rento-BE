package com.kbe5.api.domain.drive.controller;

import com.kbe5.api.domain.drive.dto.DriveAddRequest;
import com.kbe5.api.domain.drive.dto.DriveDetailResponse;
import com.kbe5.api.domain.drive.dto.DriveResponse;
import com.kbe5.api.domain.drive.mapper.DriveRequestMapper;
import com.kbe5.api.domain.drive.mapper.DriveResponseMapper;
import com.kbe5.common.apiresponse.ResEntityFactory;
import com.kbe5.common.response.api.ApiResponse;
import com.kbe5.common.response.api.ApiResultCode;
import com.kbe5.domain.drive.dto.DriveAddCommand;
import com.kbe5.domain.drive.service.DriveService;
import com.kbe5.infra.security.details.CustomManagerDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/drives")
public class DriveControllerImpl implements DriveController {

    private final DriveService driveService;
    private final DriveRequestMapper driveRequestMapper;
    private final DriveResponseMapper driveResponseMapper;

    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<String>> driveAdd(@RequestBody @Validated DriveAddRequest request) {
        DriveAddCommand command = driveRequestMapper.toAddCommand(request);
        driveService.driveAdd(command);

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, "운행 예약 완료");
    }

    @Override
    @DeleteMapping("/cancel/{driveId}")
    public ResponseEntity<ApiResponse<String>> driveCancel(@PathVariable Long driveId) {
        driveService.driveCancel(driveId);
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS,"운행이 취소되었습니다");
    }

    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<PagedModel<DriveResponse>>> getDriveList(
            @AuthenticationPrincipal CustomManagerDetails manager,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    ) {
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS,
                new PagedModel<>(driveService.getDriveList(
                        manager.getManager(),
                                startDate,
                                endDate,
                                pageable
                        )
                        .map(driveResponseMapper::toDriveResponse)));
    }

    @Override
    @GetMapping("/{driveId}")
    public ResponseEntity<ApiResponse<DriveDetailResponse>> getDriveDetail(@PathVariable Long driveId) {
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS,
                driveResponseMapper.toDriveDetailResponse(driveService.getDriveDetail(driveId)));
    }

    @GetMapping("/driving")
    public ResponseEntity<ApiResponse<List<DriveResponse>>> getDriving(
            @AuthenticationPrincipal CustomManagerDetails manager,
            @RequestParam(required=false) String vehicleNumber) {
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS,
                driveService.findStream(
                        manager.getManager(),
                                vehicleNumber
                        )
                        .stream()
                        .map(driveResponseMapper::toDriveResponse)
                        .toList());
    }
}
