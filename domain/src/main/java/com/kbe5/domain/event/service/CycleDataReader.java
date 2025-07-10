package com.kbe5.domain.event.service;

import com.kbe5.domain.event.entity.CycleData;

import java.util.List;

public interface CycleDataReader {

    List<CycleData> getCycleInfoListWithDrive(Long driveId);
}
