package com.kbe5.rento.common.jwt;

public class JwtProperties {

    private JwtProperties() {}

    public final static String JWT_TOKEN = "asfasdgawegasfasvevasfasf12342352rwrq23af2qf2"; // .env 파일에 실제 사용할 토큰 추가 예정
    public final static long ACCESS_EXPIRED_TIME = 600000;// 10분
    public final static long REFRESH_EXPIRED_TIME = 6000000;// 100분 (Refresh 토큰 발급 시 사용 예정)
}
