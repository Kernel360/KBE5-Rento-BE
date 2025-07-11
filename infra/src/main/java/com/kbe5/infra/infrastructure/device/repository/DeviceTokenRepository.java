package com.kbe5.infra.infrastructure.device.repository;

import com.kbe5.domain.device.entity.DeviceToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceTokenRepository {

    DeviceToken save(DeviceToken deviceToken);
    Optional<DeviceToken> findById(String token);
    void deleteById(String token);
}
