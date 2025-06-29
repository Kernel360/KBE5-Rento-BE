package com.kbe5.domain.drive.repository;

import com.kbe5.domain.company.entity.Company;
import com.kbe5.domain.drive.entity.Drive;
import com.kbe5.domain.drive.entity.DriveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DriveRepository extends JpaRepository<Drive, Long> {

    List<Drive> findByMember_Company(Company company);

    @Query("""
      SELECT d.id
      FROM Drive d
      WHERE d.mdn = :mdn
        AND d.startDate <= :startDate
    """)
    Long findIdByMdnAndStartDateBetween(@Param("mdn") Long mdn,
                                        @Param("startDate") LocalDateTime startDate
    );

    List<Drive> findByDriveStatus(DriveStatus driveStatus);
    @Query("""
  select case when count(d) > 0 then true else false end
  from Drive d
  where d.startDate <= :end
    and d.endDate   >= :start
""")
    boolean existsByDateOverlap(
            @Param("start") LocalDateTime start,
            @Param("end")   LocalDateTime end
    );

    @Query("""
  select case when count(d) > 0 then true else false end
  from Drive d
  where d.vehicle.id = :vehicleId
    and :start between d.startDate and d.endDate
    or :end between d.startDate and d.endDate
""")
    boolean existsByVehicleOverlap(
            @Param("vehicleId") Long vehicleId,
            @Param("start")     LocalDateTime start,
            @Param("end")       LocalDateTime end
    );

    @Query("""
    select d
      from Drive d
        where d.member.company    = :company
          and d.driveStatus       = :status
            and (:vehNum    is null or d.vehicle.info.vehicleNumber = :vehNum)
  """)
    List<Drive> findByCompanyAndStatusAndVehicleNumber(
            @Param("company")     Company     company,
            @Param("status")      DriveStatus status,
            @Param("vehNum")      String      vehicleNumber    // null 이면 차량번호 조건 무시
    );
}
