package com.kbe5.rento.domain.geofence.service;

import com.kbe5.rento.common.exception.DomainException;
import com.kbe5.rento.common.exception.ErrorType;
import com.kbe5.rento.domain.geofence.entity.Geofence;
import com.kbe5.rento.domain.geofence.repository.GeofenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GeofenceService {

    private final GeofenceRepository geofenceRepository;

    public void register(Geofence geofence) {
        geofenceRepository.save(geofence);
    }

    @Transactional(readOnly = true)
    public List<Geofence> getGeofenceList(String companyCode) {
        List<Geofence> geofenceList = geofenceRepository.findAllByCompanyCode(companyCode);

        if (geofenceList.isEmpty()) {
            throw new DomainException(ErrorType.GEOFENCE_NOT_FOUND);
        }

        return geofenceList;
    }

    @Transactional(readOnly = true)
    public Geofence getGeofenceDetail(Long id) {

        return geofenceRepository.findById(id)
                .orElseThrow(() -> new DomainException(ErrorType.COMPANY_NOT_FOUND));
    }

    public void delete(Long id) {
        Geofence geofence = geofenceRepository.findById(id)
                        .orElseThrow(() -> new DomainException(ErrorType.GEOFENCE_NOT_FOUND));

        geofenceRepository.delete(geofence);
    }

    public void update(Long id, Geofence updatedGeofence) {
        Geofence geofence = geofenceRepository.findById(id)
                .orElseThrow(() -> new DomainException(ErrorType.GEOFENCE_NOT_FOUND));

        geofence.toUpdate(updatedGeofence);
    }
}

