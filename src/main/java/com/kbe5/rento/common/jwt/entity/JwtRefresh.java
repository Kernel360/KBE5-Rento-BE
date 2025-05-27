package com.kbe5.rento.common.jwt.entity;

import com.kbe5.rento.common.util.BaseEntity;
import com.kbe5.rento.domain.manager.entity.Manager;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "jwt_refresh_tokens")
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