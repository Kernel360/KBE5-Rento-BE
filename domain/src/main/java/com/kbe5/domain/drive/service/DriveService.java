package com.kbe5.domain.drive.service;

<<<<<<< HEAD
import java.time.LocalDateTime;

public interface DriveService {

    Long findDriveForEvent(Long mdn, LocalDateTime now);
=======
import com.kbe5.domain.drive.dto.DriveAddCommand;
import com.kbe5.domain.drive.dto.DriveInfo;
import com.kbe5.domain.manager.entity.Manager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface DriveService {
    void driveAdd(DriveAddCommand command);
    void driveCancel(Long driveId);
    Page<DriveInfo> getDriveList(
            Manager manager,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    );
    DriveInfo getDriveDetail(Long driveId);
    Long findDriveForEvent(
            Long mdn,
            LocalDateTime onTime
    );
    List<DriveInfo> findStream(
            Manager manager,
            String vehicleNumber
    );
>>>>>>> 9e9ed5b78eeefed57edf0ccb6f562520c1c429e8
}
