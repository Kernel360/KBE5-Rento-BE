package com.kbe5.domain.drive.service;

import com.kbe5.domain.drive.entity.Drive;
import com.kbe5.domain.manager.entity.Manager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface DriveReader {

    void overLapDrive(
            Long vehicleId,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime
    );
    Drive getDrive(Long id);
    Long getDriveWithMdnAndOnTime(Long mdn, LocalDateTime ontime);
    Page<Drive> getDriveList(
            Manager manager,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    );
    List<Drive> getDrivingList(Manager manager, String vehicleNumber);
}
