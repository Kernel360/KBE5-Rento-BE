package com.kbe5.infra.infrastructure.vehicle.repository;

import com.kbe5.domain.vehicle.entity.Vehicle;
import com.kbe5.domain.vehicle.entity.VehicleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findByInfo_VehicleNumber(String vehicleNumber);

    @Query("""
      select v.company.id
      from Vehicle v
      where v.mileage.mdn = :mdn
    """)
    Optional<Long> findCompanyIdByMdn(@Param("mdn") Long mdn);


    Page<Vehicle> findAllByCompanyIdAndDeleteStatus(Long companyId, Pageable pageable, boolean deleteStatus);

    Page<Vehicle> findAllByCompanyIdAndDepartmentIdAndDeleteStatus(Long companyId, Long departmentId, Pageable pageable, boolean deleteStatus);

    @Query("""
      SELECT v FROM Vehicle v
       WHERE v.company.id = :cid
         AND v.id NOT IN (
           SELECT v.id FROM Vehicle v WHERE v.status = :status
         )
         AND v.deleteStatus = false
    """)
    Page<Vehicle> findFreeByCompanyId(
            @Param("cid") Long companyId,
            @Param("status") VehicleStatus status,
            Pageable pageable
    );

    @Query("""
      SELECT v FROM Vehicle v
       WHERE v.company.id = :cid
         AND v.department.id = :did
         AND v.id NOT IN (
           SELECT v.id FROM Vehicle v WHERE v.status = :status
         )
         AND v.deleteStatus = false
    """)
    Page<Vehicle> findFreeByCompanyIdAndDepartmentId(
            @Param("cid") Long companyId,
            @Param("did") Long departmentId,
            @Param("status") VehicleStatus status,
            Pageable pageable
    );

    @Query("""
        SELECT v FROM Vehicle v
                WHERE v.company.id = :cid AND v.info.vehicleNumber like concat('%', :vnum ,'%')
                AND v.deleteStatus = false
        """)
    Page<Vehicle> findVehicleByCompanyIdAndInfo_VehicleNumber(@Param("cid") Long companyId,
                                                              @Param("vnum") String vehicleNumber,
                                                              Pageable pageable);


}
