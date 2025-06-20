package com.kbe5.api.domain.drive.controller;


import com.kbe5.api.domain.drive.dto.DriveAddRequest;
import com.kbe5.api.domain.drive.dto.DriveDetailResponse;
import com.kbe5.api.domain.drive.dto.DriveResponse;
import com.kbe5.api.domain.manager.dto.details.CustomManagerDetails;
import com.kbe5.common.response.api.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DriveController {

    ResponseEntity<ApiResponse<String>> driveAdd(DriveAddRequest request);
    ResponseEntity<ApiResponse<String>> driveCancel(Long driveId);
    ResponseEntity<ApiResponse<List<DriveResponse>>> getDriveList(CustomManagerDetails manager);
    ResponseEntity<ApiResponse<DriveDetailResponse>> getDriveDetail(Long driveId);
}
