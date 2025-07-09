package com.kbe5.domain.geofence.service;

import com.kbe5.domain.geofence.entity.Geofence;

import java.util.List;

public interface GeofenceReader {

    List<Geofence> getGeofenceList(String companyCode);

    Geofence getGeofenceDetail(Long id);
}
