package com.kbe5.domain.device.repository;

import com.kbe5.domain.device.entity.DeviceControlInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceControlInfoRepository extends JpaRepository<DeviceControlInfo, Long> {
    List<DeviceControlInfo> findAllByMdn(Long mdn);
}
