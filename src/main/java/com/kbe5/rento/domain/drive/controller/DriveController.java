package com.kbe5.rento.domain.drive.controller;

import com.kbe5.rento.common.apiresponse.ApiResponse;
import com.kbe5.rento.domain.drive.dto.DriveAddRequest;
import com.kbe5.rento.domain.drive.dto.DriveDetailResponse;
import com.kbe5.rento.domain.drive.dto.DriveResponse;
import com.kbe5.rento.domain.manager.dto.details.CustomManagerDetails;
import com.kbe5.rento.domain.manager.entity.Manager;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DriveController {

    ResponseEntity<ApiResponse<String>> driveAdd(DriveAddRequest request);
    ResponseEntity<ApiResponse<String>> driveStart(Long driveId);
    ResponseEntity<ApiResponse<String>> driveEnd(Long driveId);
    ResponseEntity<ApiResponse<String>> driveCancel(Long driveId);
    ResponseEntity<ApiResponse<List<DriveResponse>>> getDriveList(CustomManagerDetails manager);
    ResponseEntity<ApiResponse<DriveDetailResponse>> getDriveDetail(Long driveId);
}
