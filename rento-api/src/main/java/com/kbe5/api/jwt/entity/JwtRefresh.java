package com.kbe5.api.jwt.entity;

import com.kbe5.rento.common.util.BaseEntity;
import com.kbe5.rento.domain.manager.entity.Manager;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "jwt_refresh_tokens")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JwtRefresh extends BaseEntity {

    @OneToOne
    @JoinColumn(nullable = false, name = "manager_id")
    private Manager manager;

    @Column(length = 1000)
    private String refreshToken;

    @Column(length = 30)
    private Long expiredTime;

    @Builder
    private JwtRefresh(Manager manager, String refreshToken, Long expiredTime) {
        this.manager = manager;
        this.refreshToken = refreshToken;
        this.expiredTime = expiredTime;
    }
}