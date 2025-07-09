package com.kbe5.domain.geofence.service;

import com.kbe5.domain.geofence.dto.GeofenceCommand;
import com.kbe5.domain.geofence.dto.GeofenceInfo;

import java.util.List;

public interface GeofenceService {

    GeofenceInfo register(GeofenceCommand.Register command);

    GeofenceInfo update(Long id, GeofenceCommand.Update command);

    void delete(Long id);

    List<GeofenceInfo> getGeofenceList(String companyCode);

    GeofenceInfo getGeofenceDetail(Long id);
}