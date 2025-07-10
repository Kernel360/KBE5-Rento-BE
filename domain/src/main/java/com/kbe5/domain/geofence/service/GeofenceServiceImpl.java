package com.kbe5.domain.geofence.service;

import com.kbe5.domain.geofence.dto.GeofenceCommand;
import com.kbe5.domain.geofence.dto.GeofenceInfo;
import com.kbe5.domain.geofence.entity.Geofence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class GeofenceServiceImpl implements GeofenceService{

    private final GeofenceReader geofenceReader;
    private final GeofenceStore geofenceStore;

    @Override
    public GeofenceInfo register(GeofenceCommand.Register command) {
        Geofence geofence = command.toEntity();
        geofence = geofenceStore.store(geofence);

        return GeofenceInfo.fromEntity(geofence);
    }

    @Override
    public GeofenceInfo update(Long id, GeofenceCommand.Update command) {
        Geofence geofence = geofenceReader.getGeofenceDetail(id);

        command.applyTo(geofence);

        return GeofenceInfo.fromEntity(geofence);
    }

    @Override
    public void delete(Long id) {
        Geofence geofence = geofenceReader.getGeofenceDetail(id);
        geofenceStore.delete(geofence);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GeofenceInfo> getGeofenceList(String companyCode) {
        List<Geofence> geofenceList = geofenceReader.getGeofenceList(companyCode);

        return geofenceList.stream().map(GeofenceInfo::fromEntity).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public GeofenceInfo getGeofenceDetail(Long id) {
        Geofence geofence = geofenceReader.getGeofenceDetail(id);

        return GeofenceInfo.fromEntity(geofence);
    }
}
