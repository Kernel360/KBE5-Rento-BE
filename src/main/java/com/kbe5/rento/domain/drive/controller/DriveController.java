package com.kbe5.rento.domain.drive.controller;

import com.kbe5.rento.domain.drive.dto.DriveAddRequest;
import com.kbe5.rento.domain.drive.dto.DriveDetailResponse;
import com.kbe5.rento.domain.drive.dto.DriveResponse;

import java.util.List;

public interface DriveController {

    void driveAdd(DriveAddRequest request);
    void driveStart(Long driveId);
    void driveEnd(Long driveId);
    void driveCancel(Long driveId);
    List<DriveResponse> getDriveList(String companyCode);
    DriveDetailResponse getDriveDetail(Long driveId);
}
