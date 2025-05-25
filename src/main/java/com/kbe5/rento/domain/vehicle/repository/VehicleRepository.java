package com.kbe5.rento.domain.vehicle.repository;

import com.kbe5.rento.domain.company.Company;
import com.kbe5.rento.domain.vehicle.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

        List<Vehicle> findByCompany(Company company);
        Optional<Vehicle> findByVehicleNumber(String vehicleNumber);
}
