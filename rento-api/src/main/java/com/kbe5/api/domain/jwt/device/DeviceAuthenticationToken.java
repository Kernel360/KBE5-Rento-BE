package com.kbe5.api.domain.jwt.device;

import com.kbe5.rento.domain.device.entity.DeviceToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class DeviceAuthenticationToken extends AbstractAuthenticationToken {
    private final DeviceToken deviceToken; // ← 인증된 디바이스 정보 객체

    public DeviceAuthenticationToken(DeviceToken deviceToken) {
        super(null);
        this.deviceToken = deviceToken;
        setAuthenticated(true);
    }

    @Override
    public Object getPrincipal() {
        return deviceToken; // 주체가 되는 정보를 반환
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
