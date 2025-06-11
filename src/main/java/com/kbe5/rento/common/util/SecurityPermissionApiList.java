package com.kbe5.rento.common.util;

public class SecurityPermissionApiList {

    private SecurityPermissionApiList() {
    }

    public static final String[] PUBLIC_URLS = {

//            "/api/**",

//            // Manager APIs
//            "/api/managers",
//            "/api/managers/sign-up",
//            "/api/managers/login",
//            "/api/managers/logout",
//            "/api/managers/{email}",
//            "/api/managers/{loginId}",
//            "/api/managers/list/{companyCode}",
//            "/api/managers/detail/{Id}",
//
//            // Member APIs
//            "/api/members",
//            "/api/members/{memberId}",
//
//            // Vehicle APIs
//            "/api/vehicles",
//            "/api/vehicles/{vehicleId}",
//            "/api/vehicles/{vehicleNumber}",
//
//            // Emulator APIs
//            "/api/emulators/on",
//            "/api/emulators/off",
//            "/api/emulators/control-info",
//            "/api/emulators/control-info/confirm",
//            "/api/emulators/token",
//
//            // Cycle Info APIs
//            "/api/cycleinfos",
//
//            // Geofencing APIs
//            "/api/geofencings",
//            "/api/geofencings/{geofencingId}",
//            "/api/geofencings/send-geo-point",
//
//            // Location APIs
//            "/api/locations",
//
//            // Driving History APIs
//            "/api/driving-histories",
//            "/api/drivings-histories/{drivinghistory_id}",
//            "/api/driving-histories?search=",
//
//            // Reservation APIs
            "/api/reservations",
            "/api/reservations/{reservationId}",
//
//            // Company APIs
            "/api/companies/register",
            "/api/companies/check-bizNumber",
//            "/api/companies",
//            "/api/companies/{companyId}",

            // h2
            "/h2-console/**",

            // login && signUp
            "/api/managers/login",
            "/api/managers/sign-up",
            "/api/managers/check-loginId/{loginId}",
            "/api/managers/{email}",
            "/api/managers/logout",

            // refresh
            "/api/managers/refresh",

            // device
            "/api/devices", //regiset
            "/api/devices/token", //token

            //swagger
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-ui/index.html",
            "/v3/api-docs",
            "/v3/api-docs/**",
    };
}
