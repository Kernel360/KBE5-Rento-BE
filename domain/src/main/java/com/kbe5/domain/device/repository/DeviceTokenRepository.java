package com.kbe5.domain.device.repository;

import com.kbe5.domain.device.entity.DeviceToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceTokenRepository extends JpaRepository<DeviceToken, String> {

    void deleteByToken(String token);

    Optional<DeviceToken> findByToken(String token);
}
