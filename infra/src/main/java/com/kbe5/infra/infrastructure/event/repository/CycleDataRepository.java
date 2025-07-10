package com.kbe5.infra.infrastructure.event.repository;


import com.kbe5.domain.event.entity.CycleData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CycleDataRepository extends JpaRepository<CycleData, Long>, CycleDataJdbcRepository {

    List<CycleData> findAllByDriveId(Long driveId);

}
