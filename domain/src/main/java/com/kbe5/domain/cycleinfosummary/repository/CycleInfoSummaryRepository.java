package com.kbe5.domain.cycleinfosummary.repository;

import com.kbe5.domain.cycleinfosummary.entity.CycleInfoSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CycleInfoSummaryRepository extends JpaRepository<CycleInfoSummary, Long> {

    List<CycleInfoSummary> findAllByDriveId(Long driveId);
}
