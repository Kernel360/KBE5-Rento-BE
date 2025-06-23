package com.kbe5.domain.device.repository;

import com.kbe5.domain.device.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    Optional<Device> findByMdn(Long mdn);
}
