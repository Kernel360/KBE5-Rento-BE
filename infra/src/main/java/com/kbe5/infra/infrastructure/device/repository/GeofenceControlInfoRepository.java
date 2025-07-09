package com.kbe5.infra.infrastructure.device.repository;

import com.kbe5.domain.device.entity.GeofenceControlInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeofenceControlInfoRepository extends JpaRepository<GeofenceControlInfo, Long> {
    List<GeofenceControlInfo> findAllByMdn(Long mdn);
}
