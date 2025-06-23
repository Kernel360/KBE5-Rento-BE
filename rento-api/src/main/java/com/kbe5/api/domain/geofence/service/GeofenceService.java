package com.kbe5.api.domain.geofence.service;


import com.kbe5.api.domain.geofence.dto.request.GeofenceUpdateRequest;
import com.kbe5.api.domain.geofence.vo.GeofenceUpdateVO;
import com.kbe5.common.exception.DomainException;
import com.kbe5.common.exception.ErrorType;
import com.kbe5.domain.geofence.entity.Geofence;
import com.kbe5.domain.geofence.repository.GeofenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
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

    public void update(Long id, GeofenceUpdateRequest request) {
        Geofence geofence = geofenceRepository.findById(id)
                .orElseThrow(() -> new DomainException(ErrorType.GEOFENCE_NOT_FOUND));

        GeofenceUpdateVO vo = new GeofenceUpdateVO(request);
        vo.update(geofence);
    }
}

