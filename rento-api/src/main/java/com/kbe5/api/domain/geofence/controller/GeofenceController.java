package com.kbe5.api.domain.geofence.controller;


import com.kbe5.api.domain.geofence.dto.request.GeofenceRegisterRequest;
import com.kbe5.api.domain.geofence.dto.request.GeofenceUpdateRequest;
import com.kbe5.api.domain.geofence.dto.response.GeofenceResponse;
import com.kbe5.common.response.api.ApiResponse;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Hidden
public interface GeofenceController {

    public ResponseEntity<ApiResponse<GeofenceResponse>> register(GeofenceRegisterRequest request);

    public ResponseEntity<ApiResponse<List<GeofenceResponse>>> getList(String companyCode);

    public ResponseEntity<ApiResponse<GeofenceResponse>> getDetail(Long id);

    public ResponseEntity<ApiResponse<Void>> delete(Long id);

    public ResponseEntity<ApiResponse<GeofenceResponse>> update(Long id, GeofenceUpdateRequest request);
}
