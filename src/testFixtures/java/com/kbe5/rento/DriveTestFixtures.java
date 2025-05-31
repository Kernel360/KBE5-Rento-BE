package com.kbe5.rento;

import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.drive.entity.Drive;

import static com.kbe5.rento.MemberTestFixtures.memberA;
import static com.kbe5.rento.VehicleTestFixtures.vehicleA;

public class DriveTestFixtures {

    public static Drive driveA(Company company){
        return Drive.builder()
                .member(memberA(company))
                .vehicle(vehicleA())
                .build();
    }
}
