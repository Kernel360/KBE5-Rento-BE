package com.kbe5.domain.geofence.service;

import com.kbe5.domain.geofence.entity.Geofence;

public interface GeofenceStore {

    Geofence store(Geofence geofence);
    void delete(Geofence geofence);
}
