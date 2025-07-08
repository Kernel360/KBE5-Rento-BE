package com.kbe5.domain.vehicle.service;

import com.kbe5.domain.company.entity.Company;
import com.kbe5.domain.department.entity.Department;
import com.kbe5.domain.vehicle.dto.VehicleAddCommand;
import com.kbe5.domain.vehicle.entity.Vehicle;

public interface VehicleStore {

    Vehicle addVehicle(VehicleAddCommand command, Company company, Department department);
}
