package com.kbe5.rento.domain.event.repository;

import com.kbe5.rento.domain.event.entity.CycleInfo;

import java.util.List;

public interface CycleInfoJdbcRepository {

    void bulkInsert(List<CycleInfo> cycleInfo);
}
