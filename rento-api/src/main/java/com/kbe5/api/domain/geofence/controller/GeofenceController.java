package com.kbe5.api.domain.geofence.controller;


import com.kbe5.api.domain.geofence.dto.request.GeofenceRegisterRequest;
import com.kbe5.api.domain.geofence.dto.request.GeofenceUpdateRequest;
import com.kbe5.api.domain.geofence.dto.response.GeofenceInfoResponse;
import com.kbe5.common.response.api.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GeofenceController {

    public ResponseEntity<ApiResponse<Void>> register(GeofenceRegisterRequest request);

    public ResponseEntity<ApiResponse<List<GeofenceInfoResponse>>> getList(String companyCode);

    public ResponseEntity<ApiResponse<GeofenceInfoResponse>> getDetail(Long id);

    public ResponseEntity<ApiResponse<Void>> delete(Long id);

    public ResponseEntity<ApiResponse<Void>> update(Long id, GeofenceUpdateRequest request);
}
