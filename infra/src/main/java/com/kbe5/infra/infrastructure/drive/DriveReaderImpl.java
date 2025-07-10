package com.kbe5.infra.infrastructure.drive;

import com.kbe5.domain.drive.entity.Drive;
import com.kbe5.domain.drive.entity.DriveStatus;
import com.kbe5.domain.drive.service.DriveReader;
import com.kbe5.domain.exception.DomainException;
import com.kbe5.domain.exception.ErrorType;
import com.kbe5.domain.manager.entity.Manager;
import com.kbe5.infra.infrastructure.drive.repository.DriveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DriveReaderImpl implements DriveReader {

    private final DriveRepository driveRepository;

    @Override
    public void overLapDrive(
            Long vehicleId,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime
    ) {
        if(driveRepository.existsByVehicleOverlap(
                vehicleId,
                startDateTime,
                endDateTime
        )) {
            throw new DomainException(ErrorType.DRIVE_IS_FOUND);
        }
    }

    @Override
    public Drive getDrive(Long id) {
        return driveRepository.findById(id).orElseThrow(
                () -> new DomainException(ErrorType.DRIVE_NOT_FOUND)
        );
    }

    @Override
    public Long getDriveWithMdnAndOnTime(Long mdn, LocalDateTime onTime) {
        Long driveId = driveRepository.findIdByMdnAndStartDateBetween(mdn, onTime);
        if(driveId == null){
            throw new DomainException(ErrorType.DRIVE_NOT_FOUND);
        }
        return driveId;
    }

    @Override
    public Page<Drive> getDriveList(
            Manager manager,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    ) {
        return driveRepository.findByCompanyAndOptionalDateRange(
                manager.getCompany(),
                startDate,
                endDate,
                pageable
        );
    }

    @Override
    public List<Drive> getDrivingList(Manager manager, String vehicleNumber) {
        return driveRepository.findByCompanyAndStatusAndVehicleNumber(manager.getCompany(),
                vehicleNumber, DriveStatus.DRIVING);
    }

    @Override
    public List<Drive> findByDriveStatus(DriveStatus driveStatus) {
        return driveRepository.findByDriveStatus(driveStatus);
    }
}
