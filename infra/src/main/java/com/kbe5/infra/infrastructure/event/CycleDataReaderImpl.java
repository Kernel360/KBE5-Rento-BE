package com.kbe5.infra.infrastructure.event;

import com.kbe5.domain.event.entity.CycleData;
import com.kbe5.domain.event.service.CycleDataReader;
import com.kbe5.infra.infrastructure.event.repository.CycleDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CycleDataReaderImpl implements CycleDataReader {

    private final CycleDataRepository cycleInfoRepository;

    @Override
    public List<CycleData> getCycleInfoListWithDrive(Long driveId) {
        return cycleInfoRepository.findAllByDriveId(driveId);
    }
}
