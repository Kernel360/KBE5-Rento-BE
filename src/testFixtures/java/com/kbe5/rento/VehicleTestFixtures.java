package com.kbe5.rento;

import com.kbe5.rento.domain.vehicle.dto.request.VehicleAddRequest;
import com.kbe5.rento.domain.vehicle.entity.*;

import static com.kbe5.rento.CompanyTestFixtures.companyA;

public class VehicleTestFixtures {

    public static Vehicle vehicleA(){
        VehicleInfo info = new VehicleInfo("11가 1111", "현대",
                "아반떼", VehicleType.EV, FuelType.DIESEL);

        VehicleMilleage milleage = new VehicleMilleage(2123123L, "1232W");

        return Vehicle.of(companyA(), info, milleage);
    }

    public static VehicleAddRequest vehicleAddRequest(){
        return new VehicleAddRequest(1L, "11가 123", "현대",
                "소나타",VehicleType.EV, FuelType.LPG,123123L,
                "1111W");
    }
}
