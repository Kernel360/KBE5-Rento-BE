package com.kbe5.rento.domain.device.repository;

import com.kbe5.rento.domain.device.entity.event.DeviceEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceEventRepository extends JpaRepository<DeviceEvent, Long> {

}
