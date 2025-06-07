package com.kbe5.rento.domain.event.controller;

import com.kbe5.rento.domain.device.entity.DeviceToken;
import com.kbe5.rento.domain.event.dto.request.geofence.GeofenceEventRequest;
import com.kbe5.rento.domain.event.dto.response.EventResponse;
import org.springframework.http.ResponseEntity;

public interface GeofenceEventController {

    ResponseEntity<EventResponse> receiveGeofenceEvent(DeviceToken deviceToken, GeofenceEventRequest request);
}
