package com.kbe5.rento.common.jwt.respository;

import com.kbe5.rento.common.jwt.entity.JwtRefresh;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtRefreshRepository extends JpaRepository<JwtRefresh, Long> {

    Boolean existsByRefreshToken(String token);
}