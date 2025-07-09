package com.kbe5.api.domain.geofence.controller;

import com.kbe5.api.domain.geofence.dto.request.GeofenceRegisterRequest;
import com.kbe5.api.domain.geofence.dto.request.GeofenceUpdateRequest;
import com.kbe5.api.domain.geofence.dto.response.GeofenceResponse;
import com.kbe5.api.domain.geofence.mapper.GeofenceRequestMapper;
import com.kbe5.api.domain.geofence.mapper.GeofenceResponseMapper;
import com.kbe5.common.apiresponse.ResEntityFactory;
import com.kbe5.common.response.api.ApiResponse;
import com.kbe5.common.response.api.ApiResultCode;
import com.kbe5.domain.geofence.dto.GeofenceCommand;
import com.kbe5.domain.geofence.dto.GeofenceInfo;
import com.kbe5.domain.geofence.service.GeofenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/geofences")
public class GeofenceControllerImpl implements GeofenceController{

    private final GeofenceService geofenceService;

    @Override
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<GeofenceResponse>> register(@RequestBody @Valid
                                                      GeofenceRegisterRequest request) {
        GeofenceCommand.Register command = GeofenceRequestMapper.toCommand(request);
        GeofenceInfo info = geofenceService.register(command);

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, GeofenceResponseMapper.toResponse(info));
    }

    @Override
    @GetMapping("/get-list/{companyCode}")
    public ResponseEntity<ApiResponse<List<GeofenceResponse>>> getList(@PathVariable String companyCode) {
        List<GeofenceInfo> infoList = geofenceService.getGeofenceList(companyCode);

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, GeofenceResponseMapper.toResponseList(infoList));
    }

    @Override
    @GetMapping("/get-detail/{id}")
    public ResponseEntity<ApiResponse<GeofenceResponse>> getDetail(@PathVariable Long id) {
        GeofenceInfo info = geofenceService.getGeofenceDetail(id);

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, GeofenceResponseMapper.toResponse(info));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        geofenceService.delete(id);

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, null);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<GeofenceResponse>> update(@PathVariable Long id,
                                                    @RequestBody @Valid GeofenceUpdateRequest request) {
        GeofenceCommand.Update command = GeofenceRequestMapper.toCommand(request);
        GeofenceInfo info = geofenceService.update(id, command);

        return ResEntityFactory.toResponse(ApiResultCode.SUCCESS, GeofenceResponseMapper.toResponse(info));
    }
}
