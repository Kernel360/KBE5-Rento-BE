package com.kbe5.rento.domain.vehicle.repository;

import com.kbe5.rento.domain.company.entity.Company;
import com.kbe5.rento.domain.vehicle.entity.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

        List<Vehicle> findByCompany(Company company);
        Optional<Vehicle> findByInfo_VehicleNumber(String vehicleNumber);

        Page<Vehicle> findAllByCompanyId(Long companyId, Pageable pageable);

        Page<Vehicle> findAllByCompanyIdAndDepartmentId(Long companyId, Long departmentId, Pageable pageable);

        @Query("""
      SELECT v FROM Vehicle v
       WHERE v.company.id = :cid
         AND v.id NOT IN (
           SELECT d.vehicle.id FROM Drive d WHERE d.isStart = true
         )
    """)
        Page<Vehicle> findFreeByCompanyId(
                @Param("cid") Long companyId,
                Pageable pageable
        );

        @Query("""
      SELECT v FROM Vehicle v
       WHERE v.company.id = :cid
         AND v.department.id = :did
         AND v.id NOT IN (
           SELECT d.vehicle.id FROM Drive d WHERE d.isStart = true
         )
    """)
        Page<Vehicle> findFreeByCompanyIdAndDepartmentId(
                @Param("cid") Long companyId,
                @Param("did") Long departmentId,
                Pageable pageable
        );

        Optional<Vehicle> findByInfoModelName(String modelName);
}
