package com.kbe5.infra.infrastructure.geofence;

import com.kbe5.domain.exception.DomainException;
import com.kbe5.domain.exception.ErrorType;
import com.kbe5.domain.geofence.entity.Geofence;
import com.kbe5.infra.infrastructure.geofence.repository.GeofenceRepository;
import com.kbe5.domain.geofence.service.GeofenceReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeofenceReaderImpl implements GeofenceReader {

    private final GeofenceRepository geofenceRepository;

    @Override
    public List<Geofence> getGeofenceList(String companyCode) {
        return geofenceRepository.findAllByCompanyCode(companyCode);
    }

    @Override
    public Geofence getGeofenceDetail(Long id) {
        return geofenceRepository.findById(id).orElseThrow(() -> new DomainException(ErrorType.GEOFENCE_NOT_FOUND));
    }
}
