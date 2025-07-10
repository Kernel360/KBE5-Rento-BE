package com.kbe5.infra.infrastructure.event.repository;


import com.kbe5.domain.event.entity.CycleData;

import java.util.List;

public interface CycleInfoJdbcRepository {

    void bulkInsert(List<CycleData> cycleData);
}
