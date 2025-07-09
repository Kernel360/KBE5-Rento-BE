package com.kbe5.infra.infrastructure.geofence;

import com.kbe5.domain.geofence.entity.Geofence;
import com.kbe5.infra.infrastructure.geofence.repository.GeofenceRepository;
import com.kbe5.domain.geofence.service.GeofenceStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeofenceStoreImpl implements GeofenceStore {

    private final GeofenceRepository geofenceRepository;

    @Override
    public Geofence store(Geofence geofence) {
        return geofenceRepository.save(geofence);
    }

    @Override
    public void delete(Geofence geofence) {
        geofenceRepository.delete(geofence);
    }
}
