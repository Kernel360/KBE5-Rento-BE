package com.kbe5.rento.domain.drive.controller;

import com.kbe5.rento.common.apiresponse.ResEntityFactory;
import com.kbe5.rento.common.response.api.ApiResponse;
import com.kbe5.rento.common.response.api.ApiResultCode;
import com.kbe5.rento.domain.drive.dto.DriveAddRequest;
import com.kbe5.rento.domain.drive.dto.DriveDetailResponse;
import com.kbe5.rento.domain.drive.dto.DriveResponse;
import com.kbe5.rento.domain.drive.entity.Drive;
import com.kbe5.rento.domain.drive.service.DriveService;
import com.kbe5.rento.domain.manager.dto.details.CustomManagerDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/drives")
public class DriveControllerImpl implements DriveController {

    private final DriveService driveService;

    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<String>> driveAdd(@RequestBody @Validated DriveAddRequest request) {
        Drive drive = DriveAddRequest.toEntity(request);
        driveService.driveAdd(drive);

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, "운행 예약 완료");
    }

    @Override
    @PatchMapping("/cancel/{driveId}")
    public ResponseEntity<ApiResponse<String>> driveCancel(@PathVariable Long driveId) {
        driveService.driveCancel(driveId);

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS,"운행이 취소되었습니다");
    }

    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<List<DriveResponse>>> getDriveList(
            @AuthenticationPrincipal CustomManagerDetails manager) {
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, driveService
                        .getDriveList(manager.getManager())
                        .stream()
                        .map(DriveResponse::fromEntity)
                        .toList());
    }

    @Override
    @GetMapping("/{driveId}")
    public ResponseEntity<ApiResponse<DriveDetailResponse>> getDriveDetail(@PathVariable Long driveId) {
        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS,
                DriveDetailResponse.fromEntity(driveService.getDriveDetail(driveId)));
    }
}
