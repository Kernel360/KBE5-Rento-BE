package com.kbe5.rento.common.util;

public class SecurityPermissionApiList {

    private SecurityPermissionApiList() {
    }

    public static final String[] PUBLIC_URLS = {

            "/health",

            // Reservation APIs
            "/api/reservations",
            "/api/reservations/{reservationId}",

            // Company APIs
            "/api/companies/register",
            "/api/companies/check-bizNumber",

            // h2
            "/h2-console/**",

            // login && signUp
            "/api/managers/login",
            "/api/managers/sign-up",
            "/api/managers/check-loginId/{loginId}",
            "/api/managers/check-email/{email}",
            "/api/managers/logout",

            // refresh
            "/api/managers/refresh",

            // device
            "/api/devices",
            "/api/devices/token",

            //event
            "/api/events/cycle-info/get-list",

            //swagger
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-ui/index.html",
            "/v3/api-docs",
            "/v3/api-docs/**",
    };
}
