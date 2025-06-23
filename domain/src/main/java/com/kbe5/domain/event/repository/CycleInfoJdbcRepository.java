package com.kbe5.domain.event.repository;


import com.kbe5.domain.event.entity.CycleInfo;

import java.util.List;

public interface CycleInfoJdbcRepository {

    void bulkInsert(List<CycleInfo> cycleInfo);
}
