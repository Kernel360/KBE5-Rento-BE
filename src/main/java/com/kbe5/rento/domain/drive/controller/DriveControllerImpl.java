package com.kbe5.rento.domain.drive.controller;

import com.kbe5.rento.domain.drive.dto.DriveAddRequest;
import com.kbe5.rento.domain.drive.dto.DriveDetailResponse;
import com.kbe5.rento.domain.drive.dto.DriveResponse;
import com.kbe5.rento.domain.drive.entity.Drive;
import com.kbe5.rento.domain.drive.service.DriveService;
import com.kbe5.rento.domain.manager.entity.Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/drive")
public class DriveControllerImpl implements DriveController {

    private final DriveService driveService;

    @Override
    @PostMapping
    public ResponseEntity<String> driveAdd(@RequestBody @Validated DriveAddRequest request) {
        Drive drive = DriveAddRequest.toEntity(request);
        driveService.driveAdd(drive);
        return ResponseEntity.ok("운행 예약 완료");
    }

    @Override
    @PatchMapping("/start/{driveId}")
    public ResponseEntity<String> driveStart(@PathVariable Long driveId) {
        driveService.driveStart(driveId);
        return ResponseEntity.ok("시동이 켜졌습니다");
    }

    @Override
    @PatchMapping("/end/{driveId}")
    public ResponseEntity<String> driveEnd(@PathVariable Long driveId) {
        driveService.driveEnd(driveId);
        return ResponseEntity.ok("시동이 종료되었습니다");
    }

    @Override
    @PatchMapping("/cancel/{driveId}")
    public ResponseEntity<String> driveCancel(@PathVariable Long driveId) {
        driveService.driveCancel(driveId);
        return ResponseEntity.ok("운행이 취소되었습니다");
    }

    @Override
    @GetMapping
    public ResponseEntity<List<DriveResponse>> getDriveList(@AuthenticationPrincipal Manager manager) {
        return ResponseEntity.ok(driveService.getDriveList(manager));
    }

    @Override
    @GetMapping("/{driveId}")
    public ResponseEntity<DriveDetailResponse> getDriveDetail(@PathVariable Long driveId) {
        return ResponseEntity.ok(driveService.getDriveDetail(driveId));
    }
}
