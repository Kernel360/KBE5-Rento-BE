package com.kbe5.infra.infrastructure.event;

import com.kbe5.domain.event.entity.CycleInfo;
import com.kbe5.domain.event.service.CycleInfoReader;
import com.kbe5.infra.infrastructure.event.repository.CycleInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CycleInfoReaderImpl implements CycleInfoReader {

    private final CycleInfoRepository cycleInfoRepository;

    @Override
    public List<CycleInfo> getCycleInfoListWithDrive(Long driveId) {
        return cycleInfoRepository.findAllByDriveId(driveId);
    }
}
