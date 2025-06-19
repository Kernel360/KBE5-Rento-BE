package com.kbe5.api.jwt.respository;

import com.kbe5.rento.common.jwt.entity.JwtRefresh;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JwtRefreshRepository extends JpaRepository<JwtRefresh, Long> {

    Optional<JwtRefresh> findByRefreshToken(String token);
    Boolean existsByRefreshToken(String token);

    Optional<JwtRefresh> findByManagerId(Long managerId);
}