package com.kbe5.infra.infrastructure.geofence.repository;

import com.kbe5.domain.geofence.entity.Geofence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeofenceRepository extends JpaRepository<Geofence, Long> {

    List<Geofence> findAllByCompanyCode(String companyCode);
}
