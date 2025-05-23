package com.kbe5.rento.domain.drive.controller;

import com.kbe5.rento.domain.drive.dto.DriveAddRequest;
import com.kbe5.rento.domain.drive.dto.DriveDetailResponse;
import com.kbe5.rento.domain.drive.dto.DriveResponse;
import com.kbe5.rento.domain.drive.service.DriveService;
import lombok.RequiredArgsConstructor;
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
    public void driveAdd(@RequestBody @Validated DriveAddRequest request) {
        driveService.driveAdd(request);
    }

    @Override
    @PatchMapping("/start/{driveId}")
    public void driveStart(@PathVariable Long driveId) {
        driveService.driveStart(driveId);
    }

    @Override
    @PatchMapping("/end/{driveId}")
    public void driveEnd(@PathVariable Long driveId) {
        driveService.driveEnd(driveId);
    }

    @Override
    @PatchMapping("/cancel/{driveId}")
    public void driveCancel(@PathVariable Long driveId) {
        driveService.driveCancel(driveId);
    }

    @Override
    @GetMapping
    public List<DriveResponse> getDriveList(String companyCode) {
        return driveService.getDriveList(companyCode);
    }

    @Override
    @GetMapping("/{driveId}")
    public DriveDetailResponse getDriveDetail(@PathVariable Long driveId) {
        return driveService.getDriveDetail(driveId);
    }
}
