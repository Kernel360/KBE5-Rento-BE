package com.kbe5.infra.infrastructure.event.repository;


import com.kbe5.domain.event.entity.CycleInfo;
import com.kbe5.domain.event.repository.CycleInfoJdbcRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CycleInfoRepository extends JpaRepository<CycleInfo, Long>, CycleInfoJdbcRepository {

    List<CycleInfo> findAllByDriveId(Long driveId);

}
