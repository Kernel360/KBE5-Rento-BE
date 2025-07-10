package com.kbe5.infra.infrastructure.cycleDataSummary.repository;

import com.kbe5.domain.cycleinfosummary.entity.CycleDataSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CycleInfoSummaryRepository extends JpaRepository<CycleDataSummary, Long> {

    List<CycleDataSummary> findAllByDriveId(Long driveId);
}
