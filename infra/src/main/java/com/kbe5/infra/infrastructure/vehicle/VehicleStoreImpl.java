package com.kbe5.infra.infrastructure.vehicle;

import com.kbe5.domain.company.entity.Company;
import com.kbe5.domain.vehicle.dto.VehicleAddCommand;
import com.kbe5.domain.vehicle.entity.Vehicle;
import com.kbe5.domain.vehicle.service.VehicleStore;
import com.kbe5.infra.infrastructure.vehicle.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VehicleStoreImpl implements VehicleStore {

    private final VehicleRepository vehicleRepository;

    @Override
    public Vehicle addVehicle(VehicleAddCommand command, Company company) {
        Vehicle vehicle = command.toEntity(company);
        return vehicleRepository.save(vehicle);
    }
}
