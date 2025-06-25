package com.kbe5.api.domain.jwt.util;

public class JwtProperties {

    private JwtProperties() {}

    public final static long ACCESS_EXPIRED_TIME = 1800000;// 30분
    public final static long REFRESH_EXPIRED_TIME = 6000000;// 100분
}
