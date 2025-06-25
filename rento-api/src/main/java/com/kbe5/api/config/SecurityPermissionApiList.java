package com.kbe5.api.config;

public class SecurityPermissionApiList {

    private SecurityPermissionApiList() {
    }

    public static final String[] PUBLIC_URLS = {

            "/health",
            "/actuator/**",
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
            "/api/tokens/refresh",

            // device
            "/api/devices", //regiset
            "/api/devices/token", //token

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
