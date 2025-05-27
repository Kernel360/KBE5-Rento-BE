package com.kbe5.rento.domain.drive.controller;

import com.kbe5.rento.domain.drive.dto.DriveAddRequest;
import com.kbe5.rento.domain.drive.dto.DriveDetailResponse;
import com.kbe5.rento.domain.drive.dto.DriveResponse;
import com.kbe5.rento.domain.manager.entity.Manager;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DriveController {

    ResponseEntity<String> driveAdd(DriveAddRequest request);
    ResponseEntity<String> driveStart(Long driveId);
    ResponseEntity<String> driveEnd(Long driveId);
    ResponseEntity<String> driveCancel(Long driveId);
    ResponseEntity<List<DriveResponse>> getDriveList(Manager manager);
    ResponseEntity<DriveDetailResponse> getDriveDetail(Long driveId);
}
