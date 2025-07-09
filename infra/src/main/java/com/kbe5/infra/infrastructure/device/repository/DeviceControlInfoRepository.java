package com.kbe5.infra.infrastructure.device.repository;

import com.kbe5.domain.device.entity.DeviceControlInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceControlInfoRepository extends JpaRepository<DeviceControlInfo, Long> {
    List<DeviceControlInfo> findAllByMdn(Long mdn);
}
