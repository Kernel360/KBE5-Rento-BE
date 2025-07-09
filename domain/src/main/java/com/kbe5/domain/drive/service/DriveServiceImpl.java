package com.kbe5.domain.drive.service;

import com.kbe5.domain.drive.dto.DriveAddCommand;
import com.kbe5.domain.drive.dto.DriveInfo;
import com.kbe5.domain.drive.entity.Drive;
import com.kbe5.domain.manager.entity.Manager;
import com.kbe5.domain.vehicle.entity.Vehicle;
import com.kbe5.domain.vehicle.service.VehicleReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DriveServiceImpl implements DriveService {

    private final DriveStore driveStore;
    private final DriveReader driveReader;
    private final VehicleReader vehicleReader;

    @Override
    @Transactional
    public void driveAdd(DriveAddCommand command) {

        Vehicle vehicle = vehicleReader.getVehicle(command.getVehicle().getId());

        driveReader.overLapDrive(vehicle.getId(), command.getStartDateTime(), command.getEndDateTime());

        Drive drive = driveStore.addDrive(command);
        drive.addMdn(vehicle.getMileage().getMdn());
    }

    @Override
    public void driveCancel(Long driveId) {
        driveStore.deleteDrive(driveId);
    }

    @Override
    public Page<DriveInfo> getDriveList(
            Manager manager,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    ) {
        Page<Drive> driveList = driveReader.getDriveList(manager, startDate, endDate, pageable);
        return driveList.map(DriveInfo::fromEntity);
    }

    @Override
    public DriveInfo getDriveDetail(Long driveId) {
        return DriveInfo.fromEntity(driveReader.getDrive(driveId));
    }

    @Override
    public Long findDriveForEvent(Long mdn, LocalDateTime onTime) {
        return driveReader.getDriveWithMdnAndOnTime( mdn, onTime);
    }

    @Override
    public List<DriveInfo> findStream(Manager manager, String vehicleNumber) {
        List<Drive> driveList = driveReader.getDrivingList(manager, vehicleNumber);
        return driveList.stream().map(DriveInfo::fromEntity).toList();
    }
}
