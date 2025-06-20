package com.kbe5.domain.event.repository;


import com.kbe5.domain.event.entity.CycleInfo;
import com.kbe5.domain.event.entity.CycleInfoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CycleInfoRepository extends JpaRepository<CycleInfo, CycleInfoId>, CycleInfoJdbcRepository {

}
