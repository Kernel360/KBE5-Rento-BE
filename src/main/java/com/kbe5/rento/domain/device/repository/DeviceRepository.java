package com.kbe5.rento.domain.device.repository;

import com.kbe5.rento.domain.device.entity.Device;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    Optional<Device> findByMdn(Long mdn);
}
