package com.kbe5.infra.infrastructure.event.repository;


import com.kbe5.domain.event.entity.CycleData;

import java.util.List;

public interface CycleDataJdbcRepository {

    void bulkInsert(List<CycleData> cycleData);
}
