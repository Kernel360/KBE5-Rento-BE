package com.kbe5.rento.domain.vehicle.repository;

import com.kbe5.rento.domain.vehicle.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {


        @Query(value = """
    SELECT v FROM Vehicle v
     JOIN v.company c
     WHERE c.companyCode = :code
  """, nativeQuery = true)
        List<Vehicle> findByCompanyCode(@Param("code") String code);

}
