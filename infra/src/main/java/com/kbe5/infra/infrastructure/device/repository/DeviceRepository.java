package com.kbe5.infra.infrastructure.device.repository;

import com.kbe5.domain.device.entity.Device;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    Optional<Device> findByMdn(Long mdn);
}
