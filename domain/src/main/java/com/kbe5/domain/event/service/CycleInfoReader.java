package com.kbe5.domain.event.service;

import com.kbe5.domain.event.entity.CycleInfo;

import java.util.List;

public interface CycleInfoReader {

    List<CycleInfo> getCycleInfoListWithDrive(Long driveId);
}
