package com.kbe5.rento.domain.device.repository;

import com.kbe5.rento.domain.device.entity.DeviceToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceTokenRepository extends JpaRepository<DeviceToken, String> {

    void deleteByToken(String token);

    Optional<DeviceToken> findByToken(String token);
}
